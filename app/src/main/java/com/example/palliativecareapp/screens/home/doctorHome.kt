package com.example.palliativecareapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.TopicsAdapter
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.Notification.NotificationDoctorActivity
import com.example.palliativecareapp.screens.Profile
import com.example.palliativecareapp.screens.chat.DisplayUsersActivity

import com.example.palliativecareapp.screens.operations.AddTopic
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError


import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import java.util.*
import kotlin.collections.ArrayList

class DoctorHome : AppCompatActivity(),RefreshListener,TopicLoadListener{
    lateinit var db: FirebaseFirestore
    lateinit var databaseReference: DatabaseReference
     lateinit var adapter: TopicsAdapter
     lateinit var recyclerView: RecyclerView
     lateinit var searchView: SearchView
     lateinit var fab: FloatingActionButton
     lateinit var refreshListener: RefreshListener
     var displayList :  ArrayList<Topic> = ArrayList()
     var TopicsList = ArrayList<Topic>()
     lateinit var eventListener: ValueEventListener
     lateinit var topicListener: TopicLoadListener
     var isDuplicate = false

    override fun onRefresh() {
        Log.e("esr","on refresh")
//        val el :ArrayList<Topic> = ArrayList()
//        adapter = TopicsAdapter(this,el,refreshListener)
        loadTopicsFromFirebase()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home)
        refreshListener = this
        topicListener = this
        val el :ArrayList<Topic> = ArrayList()
        adapter = TopicsAdapter(this,el,refreshListener)
        adapter.setRefreshListener(this)
        recyclerView = findViewById(R.id.all_topics_rv)
        fab = findViewById(R.id.fab)
        searchView = findViewById(R.id.search)
        db = Firebase.firestore
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView.clearFocus()


        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        loadTopicsFromFirebase()
        adapter.notifyDataSetChanged()
        dialog.dismiss()
        recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks")
        dialog.show()
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //loadTopicsFromFirebase()
                adapter.notifyDataSetChanged()
                refreshListener.onRefresh()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                refreshListener.onRefresh()
                dialog.dismiss()
            }
        })
        db = Firebase.firestore
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    for(i in TopicsList){
                        Log.e("esr","topic ${i.Name.toString()}")
                    }
                    for(i in displayList){
                        Log.e("esr","display ${i.Name.toString()}")
                    }

                    Log.e("esr","have search query")
                    displayList.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    TopicsList.forEach {
                        if (it.Name!!.toLowerCase(Locale.getDefault()).contains(search)) {
                            displayList.add(it)
                        }
                    }

                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    Log.e("esr","empty search query")
                    displayList.clear()
                    displayList.addAll(TopicsList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                    recyclerView.startLayoutAnimation()


                }
                return true
            }
        })
        fab.setOnClickListener {
                val intent = Intent(this, AddTopic::class.java)
                startActivity(intent)
            }
    }
    private fun loadTopicsFromFirebase() {
        db.collection("topics")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                      val topicModel =  Topic(
                            document.id,
                            document.getString("logo"),
                            document.getString("name"),
                            document.getString("description"),
                            document.getString("content"),
                            document.getString("video"),
                        )
                    for(e in displayList){
                        if (topicModel.Name.equals(e.Name)) {
                            isDuplicate = true
                            break
                        }}
                        if (!isDuplicate) {
                            displayList.add(topicModel)
                            TopicsList.add(topicModel)
                        }


//                    val A = TopicsAdapter(this,displayList,refreshListener)
//                    recyclerView.adapter = A
//                    recyclerView.layoutManager = LinearLayoutManager(this)
                        Log.e("esr","load : diaplay list : ${document.getString("name").toString()}")

                    //Log.e("TAG", "${document.id} => ${document.data}")
                }

                topicListener.onTopicLodSuccess(displayList)

            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting documents.", exception)
            }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile -> {
                val i = Intent(this, Profile::class.java)
                startActivity(i)
            }
            R.id.message -> startActivity(Intent(this, DisplayUsersActivity::class.java))
            R.id.sort -> {
                val options = arrayOf("تصــاعدي","تنــازلي")
                val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                dialog.setTitle(" فــرزحســب ترتيــب الحروف ابجديــاً")
                    .setItems(options){ dialogInterface,  i ->
                        if (i == 0){
                            dialogInterface.dismiss()
                            sortAscending()
                        }else if (i == 1){
                            dialogInterface.dismiss()
                            sortDescending()
                        }
                    }.show()
            }
            R.id.notification -> startActivity(Intent(this, NotificationDoctorActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
    private fun sortAscending() {
        displayList.sortBy { it.Name }
        recyclerView.adapter!!.notifyDataSetChanged()
    }
    private fun sortDescending() {
        displayList.sortByDescending { it.Name }
        recyclerView.adapter!!.notifyDataSetChanged()
    }
    override fun onTopicLodSuccess(topicModelList: List<Topic>) {
        val adapter = TopicsAdapter(this,displayList!!,refreshListener)
        recyclerView.adapter = adapter
          }
    override fun onTopicLodFailed(message: String?) {
        //Snackbar.make(this@DoctorHome as View,message!!, Snackbar.LENGTH_LONG).show()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
