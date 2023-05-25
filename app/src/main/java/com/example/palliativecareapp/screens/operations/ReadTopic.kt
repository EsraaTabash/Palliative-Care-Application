package com.example.palliativecareapp.screens.operations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.R
import com.example.palliativecareapp.screens.chat.CommentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ReadTopic : AppCompatActivity() {
    lateinit var fabPatient: FloatingActionButton
    lateinit var fabPatientComment: FloatingActionButton
    lateinit var fabPatientVideo: FloatingActionButton
    lateinit var fabPatientInfograph: FloatingActionButton
    lateinit var topicFollowing: FloatingActionButton
    val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
    }
    val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
    }
    val fromRight: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_right_anim)
    }
    val toRight: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_right_anim)
    }
    var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_topic)
        fabPatient = findViewById(R.id.fabPatient)
        fabPatientComment = findViewById(R.id.fabPatientComment)
        fabPatientVideo = findViewById(R.id.fabPatientVideo)
        fabPatientInfograph = findViewById(R.id.fabPatientInfograph)
        topicFollowing = findViewById(R.id.topicFollowing)
        fabPatient.setOnClickListener {
            onButtonClicked()
        }

        var isFirstImage = true
        val intent = intent.extras
        if (intent != null) {
            val detailName = findViewById<TextView>(R.id.detailName)
            var name = intent.getString("Name")
            detailName.text = name
            val detailDescription = findViewById<TextView>(R.id.detailDescription)
            var des = intent.getString("Description")
            detailDescription.text = des
            val detailContent = findViewById<TextView>(R.id.detailContent)
            var content =  intent.getString("Content")
            detailContent.text = content
            val detailLogo = findViewById<ImageView>(R.id.detailImage)
            Glide.with(this)
                .load(intent.getString("Image"))
                .into(detailLogo)
            val detailVideo = intent.getString("Video")
            //Toast.makeText(this, intent.getString("Video").toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, intent.getStringArrayList("Info").toString()+2, Toast.LENGTH_SHORT).show()

            fabPatientComment.setOnClickListener {
                val i = Intent(this,CommentActivity::class.java)
                val name = intent?.getString("Name")
                i.putExtra("Name",name.toString())
                startActivity(i)
            }
            fabPatientVideo.setOnClickListener {
                val i = Intent(this, VideoTopic::class.java)
                Toast.makeText(this, detailVideo.toString(), Toast.LENGTH_SHORT).show()
                i.putExtra("videoUrl",detailVideo.toString())
                startActivity(i)
            }
            fabPatientInfograph.setOnClickListener {
                val i = Intent(this, InfoGraphTopic::class.java)
               // var obj = Topic(intent.getString("id"),intent.getString("Image"),name,des,content,detailVideo,intent.getFloatArray("Info"))
               // var obj = Topic(intent.getString("id"),intent.getString("Image"),name,des,content,detailVideo)
                var info = intent.getStringArrayList("Info")
                i.putStringArrayListExtra("Info", info)
                startActivity(i)

            }
            topicFollowing.setOnClickListener {

                val topicid =intent.getString("id")
                if (isFirstImage){
                    topicFollowing.setImageResource(R.drawable.ic_favorite_fill)
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if(!task.isSuccessful()){
                            Log.e("byn","faild to get token")
                            return@addOnCompleteListener
                        }
                        val token = task.result.toString()
                        Log.e("byn",token)
                        sendFCMMessage(token,"أصبحت تتابع هذا الموضوع")
                        val map = hashMapOf<String,Any>("token" to token)
                        addTokenToTopic(topicid.toString(),map,token)

                    }
//                isFirstImage = !isFirstImage
                }else{
                    topicFollowing.setImageResource(R.drawable.ic_favorite_border)
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if(!task.isSuccessful()){
                            Log.e("byn","faild to get token")
                            return@addOnCompleteListener
                        }
                        val token = task.result
                        Log.e("byn",token.toString())
                        sendFCMMessage(token.toString(),"لقد ألغيت متابعة هذا الموضوع")
                        deleteTokenFromTopic(topicid.toString(), token.toString())
                    }
                }
                isFirstImage = !isFirstImage

            }

        }


    }
    fun onButtonClicked() {
        setVisiblility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            fabPatientComment.startAnimation(fromRight)
            fabPatientVideo.startAnimation(fromRight)
            fabPatientInfograph.startAnimation(fromRight)
            topicFollowing.startAnimation(fromRight)
            fabPatient.startAnimation(rotateOpen)
        } else {
            fabPatientComment.startAnimation(toRight)
            fabPatientVideo.startAnimation(toRight)
            fabPatientInfograph.startAnimation(toRight)
            topicFollowing.startAnimation(toRight)
            fabPatient.startAnimation(rotateClose)
        }
    }

    private fun setVisiblility(clicked: Boolean) {
        if (!clicked) {
            fabPatientComment.visibility = View.VISIBLE
            fabPatientVideo.visibility = View.VISIBLE
            fabPatientInfograph.visibility = View.VISIBLE
            topicFollowing.visibility = View.VISIBLE
        } else {
            fabPatientComment.visibility = View.INVISIBLE
            fabPatientVideo.visibility = View.INVISIBLE
            fabPatientInfograph.visibility = View.INVISIBLE
            topicFollowing.visibility = View.INVISIBLE
        }
    }




    private fun addTokenToTopic(topicId:String, map: HashMap<String,Any>,token: String){
        Firebase.firestore.collection("topics").get().addOnSuccessListener {
            for (document in it){
                if (document.id == topicId){
                    var topic = Firebase.firestore.collection("topics").document(document.id).collection("tokens")

//                    topic.get().addOnSuccessListener {
//                        for(doc in it){
//                            if (doc.data["token"] == token){
//                                deleteTokenFromTopic(topicId,token)
//                            }else{
                    topic.add(map)
                        .addOnSuccessListener {
//                                        Toast.makeText(this, "token added success", Toast.LENGTH_SHORT).show()
                            Log.e("byn","token added success")
                        }.addOnFailureListener {
                            Log.e("byn","faild to add token in topic $it")
                        }
//                            }
//                        }
//                    }
                }else{
                    Log.e("byn","document not equls $document")
                }
            }
        }

    }

    private fun deleteTokenFromTopic(topicId:String, token: String){
        Firebase.firestore.collection("topics").get().addOnSuccessListener {
            for (document in it){
                if (document.id == topicId){
                    Firebase.firestore.collection("topics").document(document.id).collection("tokens").get()
                        .addOnSuccessListener {
                            for (d in it){
                                if(d.data["token"].toString() == token){
                                    Firebase.firestore.collection("topics").document(document.id).collection("tokens")
                                        .document(d.id).delete()
                                }
                            }
                        }
                }else{
                    Log.e("byn","document not equls $document")
                }
            }
        }

    }

    companion object{
        fun sendFCMMessage(token: String,text:String) {
//        val serverKey = "AAAAaf_-VAE:APA91bG53Ra9kT-QzNMGMX76rXgUt6jX7kxFferAqvG6IdpijFqhJVmhdzTQ1kjVNOE0RlqqVSGNNXuq6a0GJJcbACjZrwFtlgMVI00q54zmDAFFIRInOMXT9mv7MbWNEH2ovirpi0WP" // Replace with your FCM server key
            val serverKey = "AAAAUXgHW8k:APA91bHh76HkuqRoiQEKt2PK3Jk1AqLTqbmEP3gPCPqLeX3RaR0rdLHAn9Pvs1qL8wZNkPERrsfhv4n3_WtheZk9LKZdwgtbSJDHjydzLnFIo3M4UsVCjR6lax1MosVYuMz4FK20qa7l" // Replace with your FCM server key
            val url = URL("https://fcm.googleapis.com/fcm/send")
            GlobalScope.launch {
                val connection = url.openConnection() as HttpURLConnection
                Log.e("byn","in blobal scope")

                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.doInput = true
                connection.addRequestProperty("Content-Type", "application/json")
                connection.addRequestProperty("Authorization", "key=$serverKey")

                val message = JSONObject()
                val data = JSONObject()
                data.put("message", text)
                message.put("to", token)
                message.put("data", data)

                val messageBytes = message.toString().toByteArray(Charsets.UTF_8)
                connection.setFixedLengthStreamingMode(messageBytes.size)

                connection.connect()

                val outputStream: OutputStream = BufferedOutputStream(connection.outputStream)
                outputStream.write(messageBytes)
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Message sent successfully
                    Log.e("byn","resopns is successfully ok")
                } else {
                    // Handle failure
                    Log.e("byn","respons failed")
                }
                connection.disconnect()
            }
        }
    }



}