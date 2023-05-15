package com.example.palliativecareapp.screens.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.GroupChatAdapter
import com.example.palliativecareapp.Models.GroupMessage
import com.example.palliativecareapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GroupChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        var groupName = intent.getStringExtra("groupName")

        val ref = FirebaseDatabase.getInstance().getReference()
        val uid = Firebase.auth.uid

        val messageList = ArrayList<GroupMessage>()
        val adapter = GroupChatAdapter(this,messageList)


        val recycler_group_chat = findViewById<RecyclerView>(R.id.recycler_group_chat)
        val edit_text_group_chat = findViewById<EditText>(R.id.edit_text_group_chat)
        val button_send_group_chat = findViewById<FloatingActionButton>(R.id.button_send_group_chat)
        recycler_group_chat.adapter = adapter
        recycler_group_chat.layoutManager = LinearLayoutManager(this)
//        ref.child("groupChat").child("doctors-patient-group")
        if (groupName == null) {
            Toast.makeText(this, "group name not exist", Toast.LENGTH_SHORT).show()
            return
        }
        messageList.clear()
        ref.child("groupChat").child(groupName)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(GroupMessage::class.java)
                    if (message != null){
                        messageList.add(message)
                        adapter.notifyDataSetChanged()
                        adapter.notifyItemInserted(messageList.size -1)
                        recycler_group_chat.scrollToPosition(messageList.size -1)

                    }


                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }


            })

        button_send_group_chat.setOnClickListener {

            val message = edit_text_group_chat.text.toString()
            val timestamp = System.currentTimeMillis()
            val seconds = (timestamp/1000)%60
            val minutes = (timestamp/(1000*60)%60)
            val hours = (timestamp/(1000*60*60)%24)
            val time = "$hours:$minutes:$seconds"


            Firebase.firestore.collection("users").get().addOnSuccessListener { querySnapshot ->
//                messageList.clear()
                for (document in querySnapshot){
                    val data = document.data

//                    val email = data["email"].toString()
//                    val id = data["id"].toString().toInt()
                    val useruid = data["uid"].toString()
//                    val currentUser = UserRef("$fname $lname",email,uid!!,id)
                    if (uid == useruid){
                        val fname = data["firstName"].toString()
                        val lname = data["lastName"].toString()
                        val username = "$fname $lname"
                        val messageObject = GroupMessage(uid,username,message,time)
//                        messageList.add(messageObject)
                        if(edit_text_group_chat.text.isEmpty()) return@addOnSuccessListener
                        ref.child("groupChat").child(groupName).push()
                            .setValue(messageObject)
                        edit_text_group_chat.setText("")
                    }

                }

            }

        }

    }
}




