package com.example.palliativecareapp.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.GroupMessage
import com.example.palliativecareapp.R
import com.google.firebase.auth.FirebaseAuth


class GroupChatAdapter(val context: Context, val messageList:ArrayList<GroupMessage>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessagetxt = itemView.findViewById<TextView>(R.id.sendMessagetxt)
    }
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val group_chat_txt = itemView.findViewById<TextView>(R.id.group_chat_txt)
        val name_group_chat_txt = itemView.findViewById<TextView>(R.id.name_group_chat_txt)
        val time_group_chat = itemView.findViewById<TextView>(R.id.time_group_chat)
    }
    val num_receive = 1
    val num_sent = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.group_chat_message_item,parent,false)
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
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderUid)){
            return num_sent
        }else{
            return num_receive
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.sendMessagetxt.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.
            group_chat_txt.text =
                currentMessage.message
            viewHolder.name_group_chat_txt.text = currentMessage.senderName
            viewHolder.time_group_chat.text = currentMessage.timeStamp
            Log.d("msg", "current msg in adapter recieve ${currentMessage.message}")
            Log.d("msg", "current msg in adapter recieve text view ${holder.group_chat_txt.text.toString()}")
        }
    }
}