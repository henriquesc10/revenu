package com.example.receitas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.receitas.R
import com.example.receitas.fragments.FragmentAddProduct
import com.example.receitas.fragments.FragmentList
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()

        toolbar.setBackgroundColor(resources.getColor(R.color.gray900))
        toolbar.setTitleTextColor(resources.getColor(R.color.colorSecondary))

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_products -> {
                fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.frameLayoutMain, FragmentList.newInstance("aa","bb"))
                fragmentTransaction.addToBackStack("list")
                fragmentTransaction.commit()
            }
            R.id.nav_add_product -> {
                startActivity(Intent(this@MainActivity, AddProductActivity:: class.java))
            }
            R.id.info -> {
                startActivity(Intent(this@MainActivity, InfoActivity::class.java))
            }
            R.id.nav_exit -> {
                mAuth?.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
