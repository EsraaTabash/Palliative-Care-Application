package com.example.palliativecareapp.screens.operations

import android.app.AlertDialog
import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.activity_add_topic.*
import kotlinx.android.synthetic.main.activity_read_topic.*
import java.util.*


class AddTopic : AppCompatActivity() {
    private lateinit var uploadImage: ImageView
    private lateinit var uploadVideo: Button
    private lateinit var iconUploadImage: ImageView
    private lateinit var saveButton: Button
    private lateinit var uploadName: EditText
    private lateinit var uploadDescription: EditText
    private lateinit var uploadContent: EditText
    private lateinit var uploadInfograph: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    private var imageURL: String? = null
    private var videoURL: String? = null
    private var uri: Uri? = null
    private var videoUri: Uri? = null
    private var num1: Float = 20F
    private var num2: Float = 80F
    private var num3: Float = 100F
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_topic)
        var auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        analytics = Firebase.analytics
        analytics.logEvent("AddActivity") {
            param("userId", auth.uid.toString());
        }
        uploadImage = findViewById(R.id.uploadImage)
        uploadVideo = findViewById(R.id.uploadVideo)
        iconUploadImage = findViewById(R.id.iconUploadImage)
        uploadDescription = findViewById(R.id.uploadDescription)
        uploadName = findViewById(R.id.uploadName)
        uploadContent = findViewById(R.id.uploadContent)
        saveButton = findViewById(R.id.saveButton)
        uploadInfograph = findViewById(R.id.uploadInfo)

        num1 = intent.getStringExtra("num1")?.toFloat() ?: 20F
        num2 = intent.getStringExtra("num2")?.toFloat() ?: 80F
        num3 = intent.getStringExtra("num3")?.toFloat() ?: 100F


        Toast.makeText(this, num1.toString()+"get", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, num2.toString()+"get", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, num3.toString()+"get", Toast.LENGTH_SHORT).show()

        val videoResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val selectedVideoUri: Uri? = data?.data
                if (selectedVideoUri != null) {
                    videoUri = selectedVideoUri
                    videoURL = videoUri.toString()
                    Toast.makeText(this, videoUri.toString(), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No Video Selected", Toast.LENGTH_SHORT).show()
            }
        }

        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data?.data
                uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_GET_CONTENT)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        uploadVideo.setOnClickListener {
            val videoPicker = Intent(Intent.ACTION_GET_CONTENT)
            videoPicker.type = "video/*"
            videoResultLauncher.launch(videoPicker)
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        }

        iconUploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_GET_CONTENT)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        uploadInfograph.setOnClickListener {
            startActivity(Intent(this, AddInfoGraph::class.java))
        }

        saveButton.setOnClickListener {
            if (uploadName.text.isNullOrEmpty()) {
                uploadName.error = "Please enter the topic name!"
                uploadName.requestFocus()
            } else if (uploadDescription.text.isNullOrEmpty()) {
                uploadDescription.error = "Please enter the topic description!"
                uploadDescription.requestFocus()
            } else if (uploadContent.text.isNullOrEmpty()) {
                uploadContent.error = "Please enter the topic content!"
                uploadContent.requestFocus()
            } else {
                saveData()
            }
        }
    }

    private fun saveData() {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child("topicsVideos/${UUID.randomUUID()}")
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        videoUri?.let { video ->
            storageReference.putFile(video)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageReference.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        videoURL = downloadUri.toString()

                        uri?.let { imageUri ->
                            uploadImageData(imageUri) // Upload the image data
                        }

                        dialog.dismiss()
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        dialog.dismiss()
                        Toast.makeText(this, "Failed to upload video", Toast.LENGTH_SHORT).show()
                    }
                }
        } ?: run {
            uri?.let { imageUri ->
                uploadImageData(imageUri) // Upload the image data

                dialog.dismiss()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            } ?: run {
                dialog.dismiss()
                Toast.makeText(this, "No video or image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun uploadImageData(imageUri: Uri) {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child("topicsLogo/${UUID.randomUUID()}")

        storageReference.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                val downloadUri = taskSnapshot.storage.downloadUrl
                while (!downloadUri.isComplete) {
                    // Wait for the download URL to be available
                }

                val imageUrl = downloadUri.result.toString()

                // Now you have the image URL and video URL
                // You can proceed with storing the data in Firestore or perform any other necessary tasks
                saveTopicData(imageUrl, videoURL.toString())
            }
            .addOnFailureListener { exception ->
                Log.e("AddTopic", "Error uploading image: ${exception.message}")
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveTopicData(imageUrl: String, videoUrl: String) {
        val id = UUID.randomUUID().toString()
        val name = uploadName.text.toString()
        val desc = uploadDescription.text.toString()
        val content = uploadContent.text.toString()
        val numList = listOf(num1.toString(), num2.toString(), num3.toString())

        val topic = Topic(id, imageUrl, name, desc, content, videoUrl, numList,false)

        db.collection("topics")
            .add(topic)
            .addOnSuccessListener { documentReference ->
                Log.e("AddTopic", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DoctorHome::class.java))
            }
            .addOnFailureListener { e ->
                Log.e("AddTopic", "Error adding document", e)
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
    }

}
