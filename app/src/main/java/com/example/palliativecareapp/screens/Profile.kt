package com.example.palliativecareapp.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.palliativecareapp.Login
import com.example.palliativecareapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
     val email = findViewById<TextView>(R.id.emailProfile)
     val name = findViewById<TextView>(R.id.nameProfile)
     val address = findViewById<TextView>(R.id.addressProfile)
     val phone = findViewById<TextView>(R.id.phoneProfile)
     val birthday = findViewById<TextView>(R.id.birthdayProfile)
     var db = Firebase.firestore
     var auth = Firebase.auth
        db.collection("users")
                .whereEqualTo("uid", auth.currentUser?.uid)
                .get()
                .addOnSuccessListener { querySnapshot->
                    email.setText(querySnapshot.documents[0].getString("email").toString())
                    name.setText(querySnapshot.documents[0].getString("firstName").toString()+"  "+querySnapshot.documents[0].getString("middleName").toString()+"  "+querySnapshot.documents[0].getString("lastName").toString())
                    address.setText(querySnapshot.documents[0].getString("address").toString())
                    phone.setText(querySnapshot.documents[0].getString("phone").toString())
                    birthday.setText(querySnapshot.documents[0].getString("birthday").toString())
                }


        exit.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }



    }
}