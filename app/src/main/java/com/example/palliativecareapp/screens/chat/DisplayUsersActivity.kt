package com.example.palliativecareapp.screens.chat

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.UserChatAdapter
import com.example.palliativecareapp.Models.UserRef
import com.example.palliativecareapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DisplayUsersActivity : AppCompatActivity() {

    lateinit var progerssDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_users)
        progerssDialog = ProgressDialog(this)
        progerssDialog.setMessage("الرجاء الإنتظار")
        val recycler_view_display_users = findViewById<RecyclerView>(R.id.recycler_view_display_users)
        val userList=ArrayList<UserRef>()
        val adapter = UserChatAdapter(this,userList)
        recycler_view_display_users.adapter = adapter
        recycler_view_display_users.layoutManager = LinearLayoutManager(this)
        val auth = Firebase.auth
        val db = Firebase.firestore
        val goto_group_btin = findViewById<FloatingActionButton>(R.id.goto_group_btin)
        goto_group_btin.setOnClickListener {
            startActivity(Intent(this,DisplayGroupsActivity::class.java))
        }


        progerssDialog.show()
        
        db.collection("users").get().addOnSuccessListener {querySnapshot ->
            userList.clear()
            for (document in querySnapshot){
                if(auth.currentUser?.uid != document.data["uid"].toString()){
                    val data = document.data

                    val fname = data["firstName"].toString()
                    val lname = data["lastName"].toString()
                    val email = data["email"].toString()
                    val id = data["id"].toString().toInt()
                    val uid = data["uid"].toString()
                    userList.add(UserRef("$fname $lname",email,uid,id))
                }
            }
            progerssDialog.dismiss()
            adapter.notifyDataSetChanged()
        }




    }
}