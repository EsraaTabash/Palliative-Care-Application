package com.example.palliativecareapp.Notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.SpecificNotificationAdapter
import com.example.palliativecareapp.Adapters.UserChatAdapter
import com.example.palliativecareapp.Models.UserRef
import com.example.palliativecareapp.R
import com.example.palliativecareapp.screens.chat.DisplayGroupsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class SpecificNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_notification)

        val messageTitle = intent.getStringExtra("title").toString()
        val messageBody = intent.getStringExtra("body").toString()

        val recyclerSpecificNotification =
            findViewById<RecyclerView>(R.id.recyclerSpecificNotification)
        val userList = ArrayList<UserRef>()
        val adapter = SpecificNotificationAdapter(this, userList)
        recyclerSpecificNotification.adapter = adapter
        recyclerSpecificNotification.layoutManager = LinearLayoutManager(this)
        val auth = Firebase.auth
        val db = Firebase.firestore
        val SendspecificNotificationButton =
            findViewById<Button>(R.id.SendspecificNotificationButton)
        SendspecificNotificationButton.setOnClickListener {
//            startActivity(Intent(this, DisplayGroupsActivity::class.java))

//            FirebaseMessaging.getInstance().subscribeToTopic(topic)

            if(messageTitle.isNotEmpty() && messageBody.isNotEmpty() ) {

                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if(!task.isSuccessful()){
                        Log.e("byn","faild to get token")
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.e("byn",token.toString())

                    Firebase.firestore.collection("tokens").get().addOnSuccessListener {
                        for (document in it){
                            if (document.data["uid"] != Firebase.auth.currentUser?.uid){
                                val t = document.data["token"].toString()
                                NotificationDoctorActivity.sendFCMMessage(t, messageTitle, messageBody)
                            }

                        }
                    }

                    NotificationDoctorActivity.sendFCMMessage(token.toString(), "تم الارسال بنجاح", "تم ارسال الرسالة بنجاح")

                }


            } else {
                Toast.makeText(this, "Please enter title and message", Toast.LENGTH_SHORT).show()
            }

        }


        db.collection("tokens").get().addOnSuccessListener { querySnapshot ->
            userList.clear()
            for (document in querySnapshot) {
                if (auth.currentUser?.uid != document.data["uid"].toString()) {
                    val data = document.data

                    val name = data["name"].toString()
                    val email = data["email"].toString()
                    val id = data["id"].toString().toInt()
                    val uid = data["uid"].toString()
                    userList.add(UserRef(name, email, uid, id))
                }
            }
//            progerssDialog.dismiss()
            adapter.notifyDataSetChanged()


        }
    }
}