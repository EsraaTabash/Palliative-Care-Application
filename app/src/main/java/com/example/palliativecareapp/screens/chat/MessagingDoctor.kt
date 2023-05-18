package com.example.palliativecareapp.screens.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.Adapters.MessageChatAdapter
import com.example.palliativecareapp.Models.MessageChatData
import com.example.palliativecareapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessagingDoctor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging_doctor)

//        val messagesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_chat)
//        val messageEditText = findViewById<EditText>(R.id.edit_text_chat)
//        val sendButton = findViewById<FloatingActionButton>(R.id.button_send_chat)

        val db = Firebase.firestore
        val auth = Firebase.auth
//        mdbRef = FirebaseDatabase.getInstance().getReference()
//        auth.uid

        val id = intent.getStringExtra("uid")
        val receiverUid = id.toString()
        val senderUid = auth.currentUser?.uid.toString()

        supportActionBar?.title = intent.getStringExtra("name")

        Log.e("byn","patient receiverUid $receiverUid")
        Log.e("byn","patient senderUid $senderUid")

//        sendButton.setOnClickListener{
//            val messageText = messageEditText.text.toString().trim()
//
//            if(messageText.isNotEmpty()){
////                sendMessage(messageText,senderUid,receiverUid)
//                messageEditText.setText("")
//            }
//
//        }
//
//        val messageList = ArrayList<MessageChatData>()
//        val messageAdapter = MessageChatAdapter(this, messageList,senderUid)
//        messagesRecyclerView.layoutManager= LinearLayoutManager(this)
//        messagesRecyclerView.adapter = messageAdapter
//
////        FirebaseDatabase.getInstance().getReference("chat")
//        FirebaseDatabase.getInstance().getReference("message/$senderUid/$receiverUid")
//            .addChildEventListener(object : ChildEventListener {
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//
//                    val message = snapshot.getValue(MessageChatData::class.java)
//                    if (message != null){
//                        messageList.add(message)
//                        Log.e("byn","msg is $messageList")
//                        messageAdapter.notifyItemInserted(messageList.size -1)
//                        messagesRecyclerView.scrollToPosition(messageList.size -1)
//
//                    }
//
//                }
//
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//                }
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {
//
//                }
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })

//        val messageList = ArrayList<MessageChatData>()
//        val messageAdapter = MessageChatAdapter(this, messageList,senderUid)
//        messagesRecyclerView.layoutManager= LinearLayoutManager(this)
//        messagesRecyclerView.adapter = messageAdapter
//        FirebaseDatabase.getInstance().getReference("chat")

        val ref = FirebaseDatabase.getInstance().getReference("message/$senderUid/$receiverUid")


        ref.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

//                    val message = snapshot.getValue(MessageChatData::class.java)
//                    if (message != null){
//                        Log.e("byn","msg from ref is $message")
//                        messageList.add(message)
//                        messageAdapter.notifyItemInserted(messageList.size -1)
//                        messagesRecyclerView.scrollToPosition(messageList.size -1)
//
//                    }

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


    }

//    private fun sendMessage(messageText:String,senderUid:String,receiverUid:String){
//        val timestamp = System.currentTimeMillis()
//
//        val seconds = (timestamp / 1000) % 60
//        val minutes = (timestamp / (1000 * 60) % 60)
//        val hours = (timestamp / (1000 * 60 * 60) % 24)
//        val time = "$hours:$minutes:$seconds"
//
//        val  receiverRoom= senderUid + receiverUid
//        val  senderRoom =  receiverUid+ senderUid
//
//        val message = MessageChatData(messageText,senderUid, receiverUid,time)
////        FirebaseDatabase.getInstance().getReference("chat").push().setValue(message).addOnSuccessListener {
//////        mdbRef.child("chats").child(senderRoom).child("message").push().setValue(message).addOnSuccessListener {
//////            mdbRef.child("chats").child(receiverRoom).child("message").push().setValue(message)
//////            Log.e("byn","get chat success $it")
//////        }.addOnFailureListener {
//////            Log.e("byn","get chat faild $it")
//////
//////        }
////
////    }
//
//
//        FirebaseDatabase.getInstance().getReference("message/$senderUid/$receiverUid").push().setValue(message)
//        FirebaseDatabase.getInstance().getReference("message/$receiverUid/$senderUid").push().setValue(message)
//
//}


    private fun sendMessage(messageText:String,senderUid:String,receiverUid:String){
        val timestamp = System.currentTimeMillis()

        val seconds = (timestamp / 1000) % 60
        val minutes = (timestamp / (1000 * 60) % 60)
        val hours = (timestamp / (1000 * 60 * 60) % 24)
        val time = "$hours:$minutes:$seconds"

//        val message = MessageChatData(messageText,senderUid)
//        val message = MessageChatData(messageText,senderUid, receiverUid,time)
//        FirebaseDatabase.getInstance().getReference("chat").push().setValue(message)
        val ref = FirebaseDatabase.getInstance().getReference("message/$senderUid/$receiverUid").push()
        val toref = FirebaseDatabase.getInstance().getReference("message/$receiverUid/$senderUid").push()

//        ref.setValue(message).addOnSuccessListener { Log.e("byn","msg add success") }
//        toref.setValue(message)

    }


//    private fun msg(){
//
//    }
//}
//class ChatFromItem: Item<ViewHolder>(){
//override fun bind(viewHolder:ViewHolder,position:Int){
//
//}
//    override fun getLayout():Int{
//        return R.
//    }
}

