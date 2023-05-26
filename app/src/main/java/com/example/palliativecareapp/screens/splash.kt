package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Splash : AppCompatActivity() {
    private lateinit var analytics: FirebaseAnalytics
    var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        analytics = Firebase.analytics
        analytics.logEvent("SplashActivity") {
            param("userId", auth.uid.toString());
        }
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