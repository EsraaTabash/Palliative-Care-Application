package com.example.palliativecareapp.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.MessageChatData
import com.example.palliativecareapp.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList:ArrayList<MessageChatData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sendMessagetxt = itemView.findViewById<TextView>(R.id.sendMessagetxt)
    }
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recieveMessagetxt = itemView.findViewById<TextView>(R.id.recieveMessagetxt)
    }
    val num_receive = 1
    val num_sent = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.receive_message_chat_side_item,parent,false)
            return ReceiveViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.sent_message_chat_side_item,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return num_sent
        }else{
            return num_receive
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.sendMessagetxt.text = currentMessage.text
        }else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.
            recieveMessagetxt.text =
                currentMessage.text
            Log.d("msg", "current msg in adapter recieve ${currentMessage.text}")
            Log.d("msg", "current msg in adapter recieve text view ${holder.recieveMessagetxt.text.toString()}")
        }
    }
}