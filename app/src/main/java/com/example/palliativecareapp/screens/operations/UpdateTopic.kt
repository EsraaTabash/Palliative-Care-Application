package com.example.palliativecareapp.screens.operations

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.palliativecareapp.DoctorHome
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UpdateTopic : AppCompatActivity() {
    lateinit var updateButton: Button
    lateinit var updateName: EditText
    lateinit var updateDescription: EditText
    lateinit var updateContent: EditText
    lateinit var updateImage: ImageView
    lateinit var db: FirebaseFirestore
    var uri: Uri? = null
    var imageURL: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_topic)

        updateButton=findViewById(R.id.updateButton)

        val intent = intent.extras
        if (intent!= null) {
             updateName = findViewById<EditText>(R.id.updateName)
             updateName.setText(intent.getString("Name").toString())
             updateDescription = findViewById<EditText>(R.id.updateDescription)
             updateDescription.setText(intent.getString("Description").toString())
             updateContent = findViewById<EditText>(R.id.updateContent)
             updateContent.setText(intent.getString("Content").toString())
             updateImage = findViewById<ImageView>(R.id.updateImage)
            Glide.with(this)
                .load(intent.getString("Image"))
                .into(updateImage)
        }
        updateButton.setOnClickListener {
            saveData()
        }

    }
    fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("topicsLogo/topicLogoId"+ UUID.randomUUID().toString())
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        uri?.let {
            storageReference.putFile(it).addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val urlImage = uriTask.result
                imageURL = urlImage.toString()
                updateData()
                dialog.dismiss()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                dialog.dismiss()
            }
        }
    }
    fun updateData() {
        val id = UUID.randomUUID().toString()
        val name = updateName.text.toString().trim()
        val desc = updateDescription.text.toString().trim()
        val content = uploadContent.text.toString().trim()
        val newTopic = HashMap<String,Any>()
        newTopic["id"] = id
        newTopic["Name"] = name
        newTopic["Description"] = desc
        newTopic["Content"] = content
        newTopic["Logo"] = imageURL.toString()
//         newTopic = Topic(id,imageURL.toString(), name, desc, content)
        db.collection("topics")
            .document("")
            .update(newTopic)
            .addOnCompleteListener {
                Toast.makeText(this, "update ok", Toast.LENGTH_SHORT).show()
            }
    }

}