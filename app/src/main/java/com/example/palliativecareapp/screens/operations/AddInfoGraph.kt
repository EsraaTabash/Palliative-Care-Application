package com.example.palliativecareapp.screens.operations

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.palliativecareapp.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AddInfoGraph : AppCompatActivity() {
    private lateinit var num1: EditText
    private lateinit var num2: EditText
    private lateinit var num3: EditText
    private lateinit var saveBtn: Button
    private lateinit var analytics: FirebaseAnalytics
    var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info_graph)

        analytics = Firebase.analytics
        analytics.logEvent("AddInfoGraphActivty") {
            param("userId", auth.uid.toString());
        }

        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
        num3 = findViewById(R.id.num3)
        saveBtn = findViewById(R.id.saveinfo)
        saveBtn.setOnClickListener {
            val num1Value = num1.text.toString()
            val num2Value = num2.text.toString()
            val num3Value = num3.text.toString()

            val intent = Intent(this, AddTopic::class.java)
            intent.putExtra("num1", num1Value)
            intent.putExtra("num2", num2Value)
            intent.putExtra("num3", num3Value)
            Toast.makeText(this, num1Value, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, num2Value, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, num3Value, Toast.LENGTH_SHORT).show()

            startActivity(intent)
        }
    }
}