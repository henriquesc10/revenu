package com.example.receitas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import com.example.receitas.R
import com.example.receitas.extensions.toast
import com.example.receitas.fragments.FragmentList
import com.example.receitas.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.nav_header_main.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null
    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonProgressBar.visibility = View.INVISIBLE
        mAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)
        mUser = mAuth?.currentUser

        //VERIFICAR SE ESTA LOGADO OU NÃO
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            if (mUser != null) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        buttonSignIn.setOnClickListener(this)
        buttonPasswordForgot.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mAuth?.addAuthStateListener(mAuthStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthStateListener != null) {
            mAuth?.removeAuthStateListener(mAuthStateListener!!)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.buttonSignIn -> {
                login()
            }
            R.id.buttonPasswordForgot -> {
                Password()
            }
            R.id.buttonRegister -> {
                Register()
            }
        }
    }

    fun Password() {
        if (editTextEmail.text.toString().isEmpty()) {
            toast(R.string.password_forgot_fail)
            return
        }
        if (editTextPassword.text.isNotEmpty()) {
            toast(R.string.password_forgot_fail)
            return
        }
        mAuth?.sendPasswordResetEmail(
            editTextEmail.text.toString()
        )
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast(R.string.password_forgot_successful)
                }
            }
    }

    fun login() {
        if (editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty()) {
            toast(R.string.empty_fail)
            return
        }
        var user = User()
        user.email = editTextEmail.text.toString()
        user.password = editTextPassword.text.toString()
        buttonProgressBar.visibility = View.VISIBLE

        mAuth?.signInWithEmailAndPassword(
            user.email.toString(),
            user.password.toString()
        )
            ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {

                override fun onComplete(p0: Task<AuthResult>) {
                    buttonProgressBar.visibility = View.INVISIBLE
                    if (!p0.isSuccessful) {
                        toast(R.string.sign_in_fail)
                        return

                    }
                    toast(R.string.sign_in_successful)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            })
    }

    fun Register() {
        if (editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty()) {
            toast(R.string.empty_fail)
            return
        }
        var user = User()
        user.email = editTextEmail.text.toString()
        user.password = editTextPassword.text.toString()
        buttonProgressBar.visibility = View.VISIBLE

        mAuth?.createUserWithEmailAndPassword(
            user.email.toString(),
            user.password.toString()
        )
            ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {

                override fun onComplete(p0: Task<AuthResult>) {
                    buttonProgressBar.visibility = View.INVISIBLE
                    if (!p0.isSuccessful) {
                        toast(R.string.register_fail)
                        return
                    }
                    toast(R.string.register_successful)
                    toast(R.string.sign_in_successful)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            })
    }
}




