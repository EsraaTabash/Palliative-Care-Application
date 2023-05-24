package com.example.palliativecareapp.screens.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.CommentAdapter
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

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        val name = intent.getStringExtra("Name")
        val ref = FirebaseDatabase.getInstance().getReference()
        val uid = Firebase.auth.uid
        val messageList = ArrayList<GroupMessage>()
        val adapter = CommentAdapter(this,messageList)
        val recycler_comment = findViewById<RecyclerView>(R.id.recycler_comment)
        val edit_text_comment = findViewById<EditText>(R.id.edit_text_comment)
        val button_send_comment = findViewById<FloatingActionButton>(R.id.button_send_comment)
        recycler_comment.adapter = adapter

        recycler_comment.layoutManager = LinearLayoutManager(this)
        if (name == null) {
            Toast.makeText(this, "group name not exist", Toast.LENGTH_SHORT).show()
            return
        }
        messageList.clear()
        ref.child("topicsCommit").child(name)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(GroupMessage::class.java)
                    if (message != null){
                        messageList.add(message)
                        adapter.notifyDataSetChanged()
                        adapter.notifyItemInserted(messageList.size -1)
                        recycler_comment.scrollToPosition(messageList.size -1)

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

        button_send_comment.setOnClickListener {

            val message = edit_text_comment.text.toString()
            val timestamp = System.currentTimeMillis()
            val seconds = (timestamp/1000)%60
            val minutes = (timestamp/(1000*60)%60)
            val hours = (timestamp/(1000*60*60)%24)
            val time = "$hours:$minutes:$seconds"


            Firebase.firestore.collection("users").get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    val data = document.data
                    val useruid = data["uid"].toString()
                    if (uid == useruid){
                        val fname = data["firstName"].toString()
                        val lname = data["lastName"].toString()
                        val username = "$fname $lname"
                        val messageObject = GroupMessage(uid,username,message,time)
                        if(edit_text_comment.text.isEmpty()) return@addOnSuccessListener
                        ref.child("topicsCommit").child(name).push()
                            .setValue(messageObject)
                        edit_text_comment.setText("")
                    }

                }

            }

        }



    }
}