package com.example.palliativecareapp.Adapters

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.GroupMessage
import com.example.palliativecareapp.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CommentAdapter (val context: Context, val messageList:ArrayList<GroupMessage>):
    RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    var analytics: FirebaseAnalytics =  Firebase.analytics
    var auth = Firebase.auth

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val comment_txt = itemView.findViewById<TextView>(R.id.group_chat_txt)
        val name_comment_writer_txt = itemView.findViewById<TextView>(R.id.name_group_chat_txt)
        val time_comment = itemView.findViewById<TextView>(R.id.time_group_chat)
        val group_message_layout = itemView.findViewById<LinearLayout>(R.id.group_message_layout)
        val layout_content = itemView.findViewById<ConstraintLayout>(R.id.layout_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_chat_message_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        selectContentComment(auth.currentUser!!.uid,holder.name_comment_writer_txt.text.toString())
//         holder.layoutPosition
//        val layoutParams = holder.layoutPosition = Gravity.START
        holder.group_message_layout.gravity = Gravity.START
        holder.layout_content.setBackgroundResource(R.drawable.comment_shap)
        holder.name_comment_writer_txt.setTextColor(R.color.black.toInt())
//        holder.layout_content.setBackgroundColor(Color.LTGRAY)

        val currentMessage = messageList[position]
      holder.comment_txt.text = currentMessage.message
            holder.name_comment_writer_txt.text = currentMessage.senderName
            holder.time_comment.text = currentMessage.timeStamp
            Log.d("msg", "current msg in adapter recieve ${currentMessage.message}")
            Log.d("msg", "current msg in adapter recieve text view ${holder.comment_txt.text}")
    }
    fun selectContentComment(id:String,contentType:String){
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, id);
            param(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
        }
    }
}