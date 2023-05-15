package com.example.palliativecareapp.screens.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DoctorChat : AppCompatActivity() {

//    lateinit var adapterui: FirestoreRecyclerAdapter<User,DoctorViewHolder>
    lateinit var db:FirebaseFirestore
    lateinit var recPatientChat:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_chat)

         recPatientChat = findViewById<RecyclerView>(R.id.recycler_view_chat)

         db = Firebase.firestore
//
//        val data = ArrayList<UserMessagingData>()
//        val adapter = AllDoctorsChatAdapter(this,data)
//        db.collection("patient").get().addOnSuccessListener {querySnapshot ->
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

//        getAllPatients()
    }
//    fun getAllPatients(){
//        val query = db.collection("patient")
//        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query,User::class.java).build()
//        adapterui = object : FirestoreRecyclerAdapter<User,DoctorViewHolder>(options){
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
//                var view = LayoutInflater.from(this@DoctorChat).inflate(R.layout.user_chat_item,parent,false)
//                return DoctorViewHolder(view)
//            }
//
//            override fun onBindViewHolder(
//                holder: DoctorViewHolder,
//                position: Int,
//                model: User
//            ) {
//                val name = "${model.firstName} ${model.lastName}"
//               holder.name.text = name
//                holder.img.setImageResource(R.drawable.patient)
////                holder.img.setOnClickListener {
////                    val i = Intent(this@DoctorChat,MessagingPatient::class.java)
////                    i.putExtra("name", name)
//////            i.putExtra("id",FirebaseAuth.getInstance().currentUser?.uid.toString())
////                    i.putExtra("uid",model.uid)
////                    startActivity(i)
//
//
//                }
//
//            }
//
//        }
//        recPatientChat.layoutManager = LinearLayoutManager(this)
//        recPatientChat.adapter = adapterui


    }

//    class DoctorViewHolder(view: View):RecyclerView.ViewHolder(view){
//        var name = view.findViewById<TextView>(R.id.txtMsgDoctorName)
//        var img = view.findViewById<ImageView>(R.id.user_chat_img)
//
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapterui.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapterui.stopListening()
//    }


//}