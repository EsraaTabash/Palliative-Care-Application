package com.example.palliativecareapp.Models.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.MessageChatData
import com.example.palliativecareapp.R


class MessageChatAdapter(val context:Context, val messages:ArrayList<MessageChatData>, val currentUserUid:String):
    RecyclerView.Adapter<MessageChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val textMessage = itemView.findViewById<TextView>(R.id.sendMessagetxt)
//        val msg_item_img = itemView.findViewById<ImageView>(R.id.msg_send_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sent_message_chat_side_item,parent,false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.textMessage.text = message.text
        val layoutParams = holder.textMessage.layoutParams as LinearLayout.LayoutParams
//        layoutParams.gravity = if (message.senderId == currentUserUid){
//            holder.msg_item_img.setColorFilter(Color.GRAY)
//            Gravity.START
//            Log.e("byn","message is in if ${message.text}")
////            holder.textMessage.text = ""
//        }
//        layoutParams.gravity = if (message.senderId == currentUserUid){ Gravity.START}
//        else {
////            holder.msg_item_img.setImageResource(R.drawable.account_circle)
//            holder.msg_item_img.setColorFilter(Color.argb(255,79,185,175))
//            holder.textMessage.setBackgroundResource(R.drawable.receive_message)
//            Gravity.END
//            Log.e("byn","message is in else ${message.text}")
//        }



//        layoutParams.gravity = if(FirebaseAuth.getInstance().currentUser?.uid!!.equals(message.senderId)){
//Gravity.END
//            Log.e("byn","message is ${message.text}")
////            Gravity.END
//        }else{
//            holder.textMessage.setBackgroundResource(R.drawable.receive_message)
////            Gravity.START
//            Gravity.START
//            Log.e("byn","message is ${message.text}")
//        }


//
//        holder.textMessage.layoutParams = layoutParams
//        holder.msg_item_img.layoutParams = layoutParams
    }



}

//
//class MessageChatAdapter(val context:Context, val messages:ArrayList<MessageChatData>, val currentUserUid:String):
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    class SendViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//
//        val textSendMessage:TextView = itemView.findViewById<TextView>(R.id.sendMessagetxt)
////        val msg_item_img = itemView.findViewById<ImageView>(R.id.msg_send_img)
//
//    }
//
//    class ReceiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//
//        val textReceiveMessage:TextView = itemView.findViewById<TextView>(R.id.recieveMessagetxt)
////        val msg_item_img = itemView.findViewById<ImageView>(R.id.msg_receive_img)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        if (viewType == 1) {
//            val view = LayoutInflater.from(context).inflate(R.layout.patient_side_item,parent,false)
//            return ReceiveViewHolder(view)
//        }else {
//            val view = LayoutInflater.from(context).inflate(R.layout.doctor_side_item,parent,false)
//            return SendViewHolder(view)
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//        val message = messages[position]
//
//        if (holder.javaClass == SendViewHolder::class.java) {
//            val viewHolder = holder as SendViewHolder
//            holder.textSendMessage.text = message.text.toString()
//            Log.e("byn","message is ${message.text}")
//        }else {
//            val viewHolder = holder as ReceiveViewHolder
//            holder.textReceiveMessage.text = message.text.toString()
//            Log.e("byn","message is ${message.text}")
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val message = messages[position]
////        if(FirebaseAuth.getInstance().currentUser?.uid!!.equals(message.senderId)){
//        if(currentUserUid.equals(message.senderId)){
//            return 1 //receive
//        }else{
//            return 2 //send
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return messages.size
//    }

//    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//        val message = messages[position]
//        holder.textMessage.text = message.text
//        val layoutParams = holder.textMessage.layoutParams as LinearLayout.LayoutParams
//        layoutParams.gravity = if (message.senderId == currentUserUid) {
//            Gravity.START
//        } else {
//            holder.msg_item_img.setImageResource(R.drawable.account_circle)
//            holder.textMessage.setBackgroundResource(R.drawable.receive_message)
//            Gravity.END
//        }



//    }





//
//        layoutParams.gravity = if(FirebaseAuth.getInstance().currentUser?.uid!!.equals(message.senderId)){
//Gravity.RIGHT
////            Gravity.END
//        }else{
//            holder.textMessage.setBackgroundResource(R.drawable.receive_message)
////            Gravity.START
//            Gravity.LEFT
//        }

//        holder.textMessage.layoutParams = layoutParams
//        holder.msg_item_img.layoutParams = layoutParams
//    }



