package com.example.palliativecareapp.Adapters

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.R
import com.example.palliativecareapp.RefreshListener
import com.example.palliativecareapp.screens.operations.ReadTopic
import com.example.palliativecareapp.screens.operations.UpdateTopic
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.topic_item.view.*
import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.topic_item_patient.view.*


class TopicsAdapterPatient(var activity: Activity, var data: ArrayList<Topic>, private var refreshListener:RefreshListener,)
    : RecyclerView.Adapter <TopicsAdapterPatient.myViewHolder>() {
//    private val distinctData = data.distinct()
//    private val dData = ArrayList(distinctData)

    //var analytics: FirebaseAnalytics =  Firebase.analytics
    //var db: FirebaseFirestore = Firebase.firestore
    fun setRefreshListener(listener: RefreshListener) {
        refreshListener = listener
    }
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name=itemView.topic_name_patient
        val img=itemView.topic_logo_patient
        val description=itemView.topic_description_patient
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val root= LayoutInflater.from(activity).inflate(R.layout.topic_item_patient,parent,false)
        return myViewHolder(root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text=data[position].Name
        holder.description.text=data[position].Description
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(RoundedCorners(50))
        Glide.with(activity)
            .load(data[position].Logo)
            .apply(requestOptions)
            .into(holder.img)
        holder.itemView.setOnClickListener{
            val intent = Intent(activity, ReadTopic::class.java)
            intent.putExtra("Image", data[position].Logo)
            intent.putExtra("Description", data[position].Description)
            intent.putExtra("Name", data[position].Name)
            intent.putExtra("Content", data[position].Content)
            activity.startActivity(intent)
            //selectContentCategory(data[position].id.toString(),data[position].name!!)
//            val intent = Intent(activity, Details::class.java)
//            intent.putExtra("id",data[position].id)
//            activity.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return data.size
    }


}
