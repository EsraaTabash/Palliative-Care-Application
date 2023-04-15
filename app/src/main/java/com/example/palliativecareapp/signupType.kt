package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_type)
        //hide action bar
        supportActionBar?.hide()
        var PatientBtn = findViewById<Button>(R.id.signupPatientButton)
        var DoctorBtn = findViewById<Button>(R.id.signupDoctorButton)
        PatientBtn.setOnClickListener {
            val i = Intent (this,SignupPart1::class.java)
            i.putExtra("id",0)
        }
        DoctorBtn.setOnClickListener {
            val i = Intent (this,SignupPart1::class.java)
            i.putExtra("id",1)      }
        }
    }
