package com.example.palliativecareapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.UserRef
import com.example.palliativecareapp.R
import com.example.palliativecareapp.screens.chat.ChatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class UserChatAdapter(val context:Context, val userList:ArrayList<UserRef>):RecyclerView.Adapter<UserChatAdapter.UserChatViewHolder>() {
    var analytics: FirebaseAnalytics =  Firebase.analytics

    class UserChatViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txt_view_user_chat = itemView.findViewById<TextView>(R.id.txt_view_notification)
        val user_chat_img = itemView.findViewById<ImageView>(R.id.NotificatioUserImage)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_chat_item,parent,false)
        return UserChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserChatViewHolder, position: Int) {
        val currentUser = userList[position]
        var name = ""
        if(currentUser.id == 0){
             name = currentUser.name
            holder.user_chat_img.setImageResource(R.drawable.patient)

        }else if (currentUser.id == 1){
             name = "د. ${currentUser.name}"
            holder.user_chat_img.setImageResource(R.drawable.doctor)

        }

        holder.txt_view_user_chat.text = name
        holder.itemView.setOnClickListener {
            selectContentChat(currentUser.uid,name)
            val i = Intent(context,ChatActivity::class.java)
            i.putExtra("uid",currentUser.uid)
            i.putExtra("name",name)
            context.startActivity(i)
        }
    }
    fun selectContentChat(id:String,contentType:String){
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, id);
            param(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
        }
    }
}