package com.example.palliativecareapp.screens.operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.palliativecareapp.R
import kotlinx.android.synthetic.main.activity_read_topic.*

class ReadTopic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_topic)
        val intent = intent.extras
        if (intent!= null) {
            val detailName = findViewById<TextView>(R.id.detailName)
            detailName.text = intent.getString("Name")
            val detailDescription = findViewById<TextView>(R.id.detailDescription)
            detailDescription.text = intent.getString("Description")
            val detailContent = findViewById<TextView>(R.id.detailContent)
            detailContent.text = intent.getString("Content")
            val detailLogo = findViewById<ImageView>(R.id.detailImage)
            Glide.with(this)
                .load(intent.getString("Image"))
                .into(detailLogo)
        }
    }

    }
