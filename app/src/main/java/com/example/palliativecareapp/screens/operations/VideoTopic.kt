package com.example.palliativecareapp.screens.operations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecareapp.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.android.synthetic.main.activity_video_topic.*

class VideoTopic : AppCompatActivity() {
    var player1: SimpleExoPlayer?=null
    var videoUrl ="https://firebasestorage.googleapis.com/v0/b/fir-dd011.appspot.com/o/videos%2Fvideoplayback.mp4?alt=media&token=1bac1a9e-8712-47f7-8b12-443ecda760ed"
    var playWhenReady = true
    var currentWindow = 0
    var playBackPosition:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_topic)
        supportActionBar?.hide()

    }
    fun initVideo(){
        player1 = SimpleExoPlayer.Builder(this).build()
        video_view.player = player1

        val mediaItem = MediaItem.Builder()
            .setUri(videoUrl)
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(this)
        ).createMediaSource(mediaItem)

        player1!!.playWhenReady = playWhenReady
        player1!!.seekTo(currentWindow,playBackPosition)
        player1!!.prepare(mediaSource,false,false)
    }
    fun releaseVideo(){
        if(player1 != null){
            playWhenReady = player1!!.playWhenReady
            playBackPosition = player1!!.currentPosition
            currentWindow = player1!!.currentWindowIndex
            player1!!.release()
            player1 = null
        }
    }
    override fun onStart() {
        super.onStart()
        initVideo()
    }
    override fun onStop() {
        super.onStop()
        releaseVideo()
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }
}