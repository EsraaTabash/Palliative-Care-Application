package com.example.palliativecareapp.Models.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.UserMessagingData
import com.example.palliativecareapp.R
import com.example.palliativecareapp.screens.chat.MessagingDoctor

class AllDoctorsChatAdapter(val context: Context, private val data:ArrayList<UserMessagingData>):
    RecyclerView.Adapter<AllDoctorsChatAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val doctorName = itemView.findViewById<TextView>(R.id.txtMsgDoctorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_chat_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
//        holder.doctorName.text = item.name
//        holder.doctorName.setOnClickListener {
//            val i = Intent(context,MessagingDoctor::class.java)
//            i.putExtra("name",item.name)
////            i.putExtra("id",FirebaseAuth.getInstance().currentUser?.uid.toString())
//            i.putExtra("id",item.id)
//            context.startActivity(i)
        }

    }
//}