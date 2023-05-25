package com.example.palliativecareapp.screens.operations

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.palliativecareapp.DoctorHome
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import java.util.*

class UpdateTopic : AppCompatActivity() {
    lateinit var updateButton: Button
    lateinit var updateName: EditText
    lateinit var updateDescription: EditText
    lateinit var updateContent: EditText
    lateinit var updateImage: ImageView
    lateinit var iconUpdateImage: ImageView
    private lateinit var analytics: FirebaseAnalytics

    lateinit var db: FirebaseFirestore
    var uri: Uri? = null
    var imageURL: String? = null
    var auth = Firebase.auth
    lateinit var top: Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_topic)
        analytics = Firebase.analytics
        analytics.logEvent("UpdateActivity") {
            param("userId", auth.uid.toString());
        }
        db = Firebase.firestore
        updateButton = findViewById(R.id.updateButton)
        updateImage = findViewById(R.id.updateImage)
        iconUpdateImage = findViewById(R.id.iconUpdateImage)
        top = intent.getParcelableExtra<Topic>("topic")!!
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data?.data
                updateImage.setImageURI(uri)
            } else {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        updateImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        iconUpdateImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        val intent = intent.extras
        if (intent != null) {
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
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        if (uri != null) {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("topicsLogo/topicLogoId" + UUID.randomUUID().toString())

            storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
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
        } else {
            updateData()
            dialog.dismiss()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData() {
        val newData = HashMap<String, Any>()
        newData["name"] = updateName.text.toString().trim()
        newData["description"] = updateDescription.text.toString().trim()
        newData["content"] = updateContent.text.toString().trim()

        if (imageURL != null) {
            newData["logo"] = imageURL!!
        }

        db.collection("topics").document(top.id!!)
            .update(newData)
            .addOnSuccessListener {
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DoctorHome::class.java))

                getTokensInTopic(top.id)

            }
            .addOnFailureListener {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
    }


    private fun getTokensInTopic(topicId:String){

        val topic = Firebase.firestore.collection("topics")

        topic.get().addOnSuccessListener {
            for (document in it){
                if (document.id == topicId){
                    Firebase.firestore.collection("topics").document(document.id).collection("tokens").get()
                        .addOnSuccessListener {
                            for (d in it){
//                                sendFCMMessage(d.data["token"].toString(),"تم تحديث موضوع${document.data["name"]} ")

                                ReadTopic.sendFCMMessage(d.data["token"].toString(),"تم تحديث موضوع${document.data["name"]}")

                            }
                        }
                }
            }
        }
    }



}