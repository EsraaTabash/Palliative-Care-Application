package com.example.palliativecareapp.Adapters
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.palliativecareapp.Models.Topic
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.topic_item.view.*
import kotlin.collections.ArrayList




class TopicsAdapter (var activity: Activity, var data :ArrayList<Topic>)
    : RecyclerView.Adapter <TopicsAdapter.myViewHolder>() {
    var db: FirebaseFirestore = Firebase.firestore

    class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var logo = itemView.topic_logo
        var name = itemView.topic_name
        var description = itemView.topic_description
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val root=LayoutInflater.from(activity).inflate(com.example.palliativecareapp.R.layout.topic_item,parent,false)
            return myViewHolder(root)
        }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text = data[position].Name
        holder.description.text = data[position].Description
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(RoundedCorners(50))
        Glide.with(activity)
            .load(data[position].Logo)
            .apply(requestOptions)
            .into(holder.logo)
        }
    override fun getItemCount(): Int {
        return data.size
    }
}




