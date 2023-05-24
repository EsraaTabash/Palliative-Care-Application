package com.example.palliativecareapp.Notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.palliativecareapp.R
import org.json.JSONObject
import java.io.IOException
import javax.security.auth.callback.Callback

import android.widget.Toast
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
        val notificationButton = findViewById<Button>(R.id.notificationButton)
        notificationButton.setOnClickListener {
            val title = notificationTitle.text.toString()
            val message = notificationBody.text.toString()
//            val recipientToken = findViewById<EditText>(R.id.etToken).text.toString()

            FirebaseMessaging.getInstance().subscribeToTopic(topic)

            if(title.isNotEmpty() && message.isNotEmpty() ) {

                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if(!task.isSuccessful()){
                        Log.e("byn","faild to get token")
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.e("byn",token.toString())

//                    sendFCMMessage(token.toString(), title, message)
                    sendFCMMessage(topic, title, message)
                }


            } else {
                Toast.makeText(this, "Please enter title and message", Toast.LENGTH_SHORT).show()
            }


        }

    }



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