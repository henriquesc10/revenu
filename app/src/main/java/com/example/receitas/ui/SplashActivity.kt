package com.example.receitas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.receitas.R

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val splash_delay: Long = 2000

    internal var runnable = Runnable{
        if(!isFinishing){
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, splash_delay)

    }

    override fun onDestroy() {
        super.onDestroy()
        delayHandler?.let{
            it.removeCallbacks(runnable)
        }
    }
}