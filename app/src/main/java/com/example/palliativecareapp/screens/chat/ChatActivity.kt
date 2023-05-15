package com.example.palliativecareapp.screens.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.MessageAdapter
import com.example.palliativecareapp.Models.MessageChatData
import com.example.palliativecareapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val recycler_view_chat = findViewById<RecyclerView>(R.id.recycler_view_chat)
        val edit_text_chat = findViewById<EditText>(R.id.edit_text_chat)
        val button_send_chat = findViewById<FloatingActionButton>(R.id.button_send_chat)

        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        val receiverUid = uid
        Log.e("uid","receiver uid is $uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val dbRef = FirebaseDatabase.getInstance().getReference()
        val senderRoom = receiverUid + senderUid
        val receiverRoom = senderUid+receiverUid
        supportActionBar?.title = name

        val messageList = ArrayList<MessageChatData>()
        val messageAdapter = MessageAdapter(this,messageList)
        recycler_view_chat.layoutManager = LinearLayoutManager(this)
        recycler_view_chat.adapter = messageAdapter


        dbRef.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(MessageChatData::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                    messageAdapter.notifyItemInserted(messageList.size -1)
                    recycler_view_chat.scrollToPosition(messageList.size -1)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        button_send_chat.setOnClickListener {
            val message = edit_text_chat.text.toString()
            Log.d("msg","the message is $message")

            val timestamp = System.currentTimeMillis()
            val seconds = (timestamp / 1000) % 60
            val minutes = (timestamp / (1000 * 60) % 60)
            val hours = (timestamp / (1000 * 60 * 60) % 24)
            val time = "$hours:$minutes:$seconds"

            val messageObject = MessageChatData(message,senderUid!!,receiverUid!!,time)
            Log.d("messageobj","message text in object ${messageObject.text}")

            dbRef.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            edit_text_chat.setText("")
        }

    }
}