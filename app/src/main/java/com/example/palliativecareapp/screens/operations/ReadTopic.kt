package com.example.palliativecareapp.screens.operations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.palliativecareapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReadTopic : AppCompatActivity() {
    lateinit var fabPatient: FloatingActionButton
    lateinit var fabPatientComment: FloatingActionButton
    lateinit var fabPatientVideo: FloatingActionButton
    lateinit var fabPatientInfograph: FloatingActionButton
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
        fabPatient.setOnClickListener {
            onButtonClicked()
        }

        val intent = intent.extras
        if (intent != null) {
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
            val detailVideo = intent.getString("Video")
            //Toast.makeText(this, intent.getString("Video").toString(), Toast.LENGTH_SHORT).show()
            fabPatientComment.setOnClickListener {

            }
            fabPatientVideo.setOnClickListener {
                val i = Intent(this, VideoTopic::class.java)
                Toast.makeText(this, detailVideo.toString(), Toast.LENGTH_SHORT).show()
                i.putExtra("videoUrl",detailVideo.toString())
                startActivity(i)
            }
            fabPatientInfograph.setOnClickListener {
                val i = Intent(this, InfoGraphTopic::class.java)
                startActivity(i)

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
            fabPatient.startAnimation(rotateOpen)
        } else {
            fabPatientComment.startAnimation(toRight)
            fabPatientVideo.startAnimation(toRight)
            fabPatientInfograph.startAnimation(toRight)
            fabPatient.startAnimation(rotateClose)
        }
    }

    private fun setVisiblility(clicked: Boolean) {
        if (!clicked) {
            fabPatientComment.visibility = View.VISIBLE
            fabPatientVideo.visibility = View.VISIBLE
            fabPatientInfograph.visibility = View.VISIBLE
        } else {
            fabPatientComment.visibility = View.INVISIBLE
            fabPatientVideo.visibility = View.INVISIBLE
            fabPatientInfograph.visibility = View.INVISIBLE
        }
    }
}