package com.example.palliativecareapp.screens.operations

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.palliativecareapp.R
import kotlinx.android.synthetic.main.activity_info_graph_topic.*

class InfoGraphTopic : AppCompatActivity() {
    lateinit var d1 :TextView
    lateinit var d2 :TextView
    lateinit var d3 :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_graph_topic)
        d1 = findViewById(R.id.details1)
        d2 = findViewById(R.id.details2)
        d3 = findViewById(R.id.details3)
        donutChart.donutColors= intArrayOf(
            Color.parseColor("#b3e0dc"),
            Color.parseColor("#81cdc6"),
            Color.parseColor("#4fb9af"),
        )
        donutChart.animation.duration = 1000L
        donutChart.animate(donutSet)
            d1.setText("◀  عدد حـــالات الشفـــاء :   ${donutSet[0]}")
            d2.setText("◀  عدد الوفيــات :   ${donutSet[1]}")
            d3.setText("◀  عدد المصابيــن :   ${donutSet[2]}")
        }
    companion object{
        private val donutSet = listOf(
            20F,
            80F,
            100F
        )
    }
}