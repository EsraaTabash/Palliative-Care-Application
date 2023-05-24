package com.example.palliativecareapp.screens.operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.palliativecareapp.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.android.synthetic.main.activity_video_topic.*

class VideoTopic : AppCompatActivity() {
    private var player1: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playBackPosition: Long = 0
    private lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_topic)
        supportActionBar?.hide()
        videoUrl = intent?.getStringExtra("videoUrl").toString()
        Toast.makeText(this, videoUrl.toString(), Toast.LENGTH_SHORT).show()
        if (videoUrl == null || videoUrl=="null") {
            val msg = findViewById<TextView>(R.id.msg)
            msg.visibility = View.VISIBLE
            video_view.visibility = View.INVISIBLE
        } else {
            Toast.makeText(this, videoUrl, Toast.LENGTH_SHORT).show()
            //initVideo()
        }
    }

    private fun initVideo() {
        player1 = SimpleExoPlayer.Builder(this).build()
        video_view.player = player1

        if (videoUrl != null || videoUrl!="null") {
            val mediaItem = MediaItem.Builder()
                .setUri(videoUrl!!)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()

            val dataSourceFactory = DefaultDataSource.Factory(this)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)

            player1?.playWhenReady = playWhenReady
            player1?.seekTo(currentWindow, playBackPosition)
            player1?.setMediaSource(mediaSource)
            player1?.prepare()
        } else {
            val msg = findViewById<TextView>(R.id.msg)
            msg.visibility = View.VISIBLE
            video_view.visibility = View.INVISIBLE
        }
    }

    private fun releaseVideo() {
        player1?.let {
            playWhenReady = it.playWhenReady
            playBackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            it.release()
            player1 = null
        }
    }
//
//    override fun onStop() {
//        super.onStop()
//        releaseVideo()
//    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }

    override fun onStart() {
        super.onStart()
        initVideo()
    }
}






