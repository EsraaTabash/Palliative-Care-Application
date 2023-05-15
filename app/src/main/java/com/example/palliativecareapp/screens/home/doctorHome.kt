package com.example.palliativecareapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Adapters.TopicsAdapter
import com.example.palliativecareapp.Models.Topic


class DoctorHome : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var list: ArrayList<Topic>
    lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home)
        db = Firebase.firestore
        list = ArrayList()

        progress.visibility = View.VISIBLE
        db.collection("topics")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(
                        Topic(
                            document.getString("logo"),
                            document.getString("name"),
                            document.getString("description"),
                            document.getString("content")
                            )
                    )
                    val rv = findViewById<RecyclerView>(R.id.all_topics)
                    val A = TopicsAdapter(this, list)
                    rv.adapter = A
                    rv.layoutManager = LinearLayoutManager(this)
                    progress.visibility = View.GONE
                    Log.e("TAG", "${document.id} => ${document.data}")
                }
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
            R.id.message -> startActivity(Intent(this, DisplayUsersActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

