package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignupType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_type)
        //hide action bar
        supportActionBar?.hide()
        var PatientBtn = findViewById<Button>(R.id.signupPatientButton)
        var DoctorBtn = findViewById<Button>(R.id.signupDoctorButton)
        var loginBtn = findViewById<TextView>(R.id.loginButton)
        PatientBtn.setOnClickListener {
            val i = Intent (this,SignupPart1::class.java)
            i.putExtra("id",0)
            Toast.makeText(this, "patient", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }
        DoctorBtn.setOnClickListener {
            val i = Intent (this,SignupPart1::class.java)
            i.putExtra("id",1)
            Toast.makeText(this, "doctor", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }
        loginBtn.setOnClickListener {
            val i = Intent (this,Login::class.java)
            startActivity(i)
        }
        }
    }
