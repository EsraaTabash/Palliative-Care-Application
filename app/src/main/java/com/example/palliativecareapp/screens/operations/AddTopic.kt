package com.example.palliativecareapp.screens.operations

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.palliativecareapp.R
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.example.palliativecareapp.DoctorHome
import com.example.palliativecareapp.Models.Topic
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.grpc.LoadBalancer
import java.text.DateFormat
import java.util.*


class  AddTopic : AppCompatActivity() {
    lateinit var uploadImage: ImageView
    lateinit var iconUploadImage: ImageView
    lateinit var saveButton: Button
    lateinit var uploadName: EditText
    lateinit var uploadDescription: EditText
    lateinit var uploadContent: EditText
    var imageURL: String? = null
    var uri: Uri? = null
    lateinit var db: FirebaseFirestore
    lateinit var reference: DatabaseReference
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_topic)
        db = Firebase.firestore
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        reference = FirebaseDatabase.getInstance().getReference("topics")
        uploadImage = findViewById(R.id.uploadImage)
        iconUploadImage = findViewById(R.id.iconUploadImage)
        uploadDescription = findViewById(R.id.uploadDescription);
        uploadName = findViewById(R.id.uploadName)
        uploadContent = findViewById(R.id.uploadContent)
        saveButton = findViewById(R.id.saveButton)


        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data!!
                uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        iconUploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        saveButton.setOnClickListener {
            if (uploadName.text.isNullOrEmpty()) {
                uploadName.error = "الرجــاء كتابة اسم الموضــوع!"
                uploadName.requestFocus()
            } else if (uploadDescription.text.isNullOrEmpty()) {
                uploadDescription.error = "الرجــاء كتابة وصف الموضــوع!"
                uploadDescription.requestFocus()
            } else if (uploadContent.text.isNullOrEmpty()) {
                uploadContent.error = "الرجــاء كتابة محتـوى الموضــوع!"
                uploadContent.requestFocus()
            } else {
                   saveData()
                }
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
                uploadData()
                dialog.dismiss()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                dialog.dismiss()
            }
        }
    }
    fun uploadData() {
        val id = UUID.randomUUID().toString()
        val name = uploadName.text.toString()
        val desc = uploadDescription.text.toString()
        val content = uploadContent.text.toString()
        val topic = Topic(id,imageURL.toString(), name, desc, content)
        db.collection("topics")
            .add(topic)
            .addOnSuccessListener { documentReference ->
                Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DoctorHome::class.java))
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Error adding document", e)
            }
    }
    }



