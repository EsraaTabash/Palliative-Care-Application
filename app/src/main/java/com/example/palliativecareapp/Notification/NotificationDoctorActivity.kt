package com.example.palliativecareapp.Notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.palliativecareapp.R
import org.json.JSONObject

import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

val topic = "/topics/myTopic"
class NotificationDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_doctor)

        val notificationTitle=  findViewById<EditText>(R.id.notificationTitle)
        val notificationBody = findViewById<EditText>(R.id.notificationBody)
        val sendAllButton = findViewById<Button>(R.id.sendAllButton)
        val SendspecificButton = findViewById<Button>(R.id.SendspecificNotificationButton)
        sendAllButton.setOnClickListener {
            val title = notificationTitle.text.toString()
            val message = notificationBody.text.toString()
//            val recipientToken = findViewById<EditText>(R.id.etToken).text.toString()

//            FirebaseMessaging.getInstance().subscribeToTopic(topic)

                    val tokenList = ArrayList<String>()
            if(title.isNotEmpty() && message.isNotEmpty() ) {

                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if(!task.isSuccessful()){
                        Log.e("byn","faild to get token")
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.e("byn",token.toString())

                    Firebase.firestore.collection("tokens").get().addOnSuccessListener {
                        for (document in it){
                            val t = document.data["token"].toString()
                            Log.e("tokens",t)
                            tokenList.add(t)
                            Log.e("tokens","$tokenList")
                            sendFCMMessage(t, title, message)
                        }

                    }
                    Log.e("byn","$tokenList")



//                    sendFCMMessage(token.toString(), title, message)
//                    sendFCMMessage(token.toString(), "تم الارسال بنجاح", "تم ارسال الرسالة بنجاح")

                }

            } else {
                Toast.makeText(this, "Please enter title and message", Toast.LENGTH_SHORT).show()
            }

//            Log.e("byn","$tokenList")
//            for (t in tokenList){
//                sendFCMMessage(t, title, message)
//
//            }
        }

        SendspecificButton.setOnClickListener {
            val i = Intent(this,SpecificNotificationActivity::class.java)
            i.putExtra("title",notificationTitle.text.toString())
            i.putExtra("body",notificationBody.text.toString())
            startActivity(i)

        }

    }


    companion object{
        fun sendFCMMessage(token: String, title: String, message: String) {
            val serverKey = "AAAA75lh4V4:APA91bH2QmpdRF8JJ_mssKSC1CRAMyc58MxLGiMIK7BPS-lYCVGfaV1lGFClffkvoeVeVAzFu8wg5TofCp2pNX253Xgpu2V4q_GeoWV_xnA48kfwXlcP8c-2dulPSg2v9Jhr1Hl45G8P" // Replace with your FCM server key

            val url = URL("https://fcm.googleapis.com/fcm/send")
            GlobalScope.launch {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Authorization", "key=$serverKey")
                connection.doOutput = true

                val json = JSONObject()
                val dataJson = JSONObject()
                dataJson.put("message", message)
                dataJson.put("title", title)
                json.put("to", token)
                json.put("data", dataJson)

                val outputStream = connection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(outputStream))
                writer.write(json.toString())
                writer.flush()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Message sent successfully
                } else {
                    // Handle failure
                }

                writer.close()
                outputStream.close()
                connection.disconnect()
            }}

    }



}