package com.example.palliativecareapp.screens.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.example.palliativecareapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DisplayGroupsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_groups)

        val patient_doctor_group_layout = findViewById<LinearLayout>(R.id.patient_doctor_group_layout)
        val patients_group_layout = findViewById<LinearLayout>(R.id.patients_group_layout)
        val patientsCardGroup = findViewById<CardView>(R.id.patientsCardGroup)

        patient_doctor_group_layout.setOnClickListener {
            val i = Intent(this,GroupChatActivity::class.java)
            i.putExtra("groupName","doctors-patients-group")
            startActivity(i)
        }

        val user = Firebase.auth.currentUser?.uid
        Log.e("aa",user!!)
        Firebase.firestore.collection("patient").get().addOnSuccessListener {
            Log.e("aaa","get patient success")
            for (document in it){
            Log.e("aaa","get document success${document.data["uid"]}")
                if(document.data.get("uid").toString() == user.toString() ){
                    Log.e("aa","uid equal user")
                    Log.e("aa",document.data.get("uid").toString())

                    patients_group_layout.visibility = View.VISIBLE
                    patientsCardGroup.visibility = View.VISIBLE

                    patients_group_layout.setOnClickListener {
                        val i = Intent(this,GroupChatActivity::class.java)
                        i.putExtra("groupName","patients-group")
                        startActivity(i)
                    }

                }
            }
        }





    }
}