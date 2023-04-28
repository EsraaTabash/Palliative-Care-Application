package com.example.palliativecareapp.screens.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.User
import com.example.palliativecareapp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PatientChat : AppCompatActivity() {

    lateinit var adapterui: FirestoreRecyclerAdapter<User, PatientViewHolder>
    lateinit var db: FirebaseFirestore
    lateinit var recPatientChat:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_chat)

         recPatientChat = findViewById<RecyclerView>(R.id.recPatientChat)

         db = Firebase.firestore

//        val data = ArrayList<UserMessagingData>()
//        val adapter = AllDoctorsChatAdapter(this,data)
//        db.collection("doctor").get().addOnSuccessListener {querySnapshot ->
//            Log.e("byn","git collection success")
//            for (document in querySnapshot){
//                var name = document.data.get("firstName").toString() + " "+document.data.get("lastName").toString()
//                var id = document.data.get("uid").toString()
//                Log.e("byn","name is $name")
//                Log.e("byn","uid is $id")
//                var d = UserMessagingData(name,id)
//                data.add(d)
//                adapter.notifyDataSetChanged()
//            }
//        }.addOnFailureListener {
//            Log.e("byn","failed to git data $it")
//        }
//        Log.e("byn","data is $data")
//        recPatientChat.layoutManager = LinearLayoutManager(this)
//        recPatientChat.adapter = adapter

        getAllDoctors()

    }

    fun getAllDoctors(){
        val query = db.collection("doctor")
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query,User::class.java).build()
        adapterui = object : FirestoreRecyclerAdapter<User, PatientViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
                var view = LayoutInflater.from(this@PatientChat).inflate(R.layout.user_chat_item,parent,false)
                return PatientViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: PatientViewHolder,
                position: Int,
                model: User
            ) {
//                holder.name.text = model.firstName + " " + model.lastName
//
//                holder.img.setImageResource(R.drawable.doctor)
//                holder.img.setOnClickListener {
//                    startActivity(Intent(this@PatientChat,MessagingDoctor::class.java))

                    val name = "${model.firstName} ${model.lastName}"
                    holder.name.text = name
                    holder.img.setImageResource(R.drawable.patient)
//                    holder.img.setOnClickListener {
//                        val i = Intent(this@PatientChat,MessagingDoctor::class.java)
//                        i.putExtra("name", name)
////            i.putExtra("id",FirebaseAuth.getInstance().currentUser?.uid.toString())
//                        i.putExtra("uid",model.uid)
//                        startActivity(i)
//
//                }
            }

        }
        recPatientChat.layoutManager = LinearLayoutManager(this)
        recPatientChat.adapter = adapterui


    }
    class PatientViewHolder(view: View):RecyclerView.ViewHolder(view){
        var name = view.findViewById<TextView>(R.id.txtMsgDoctorName)
        var img = view.findViewById<ImageView>(R.id.user_chat_img)

    }

//    override fun onStart() {
//        super.onStart()
//        adapterui.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapterui.stopListening()
//    }



}