package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //hide action bar
        getSupportActionBar()?.hide()
        //delay splash screen for 3 sec
        Handler().postDelayed({
            val i = Intent(this, SignupType::class.java)
            startActivity(i)
            finish()
        }, 5000)


    }
}