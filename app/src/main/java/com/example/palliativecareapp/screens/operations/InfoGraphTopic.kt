package com.example.palliativecareapp.screens.operations

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.palliativecareapp.Models.Topic
import com.example.palliativecareapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_info_graph_topic.*

class InfoGraphTopic : AppCompatActivity() {
    private lateinit var d1: TextView
    private lateinit var d2: TextView
    private lateinit var d3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_graph_topic)
        val array = intent.getStringArrayListExtra("Info")
        val donutSet = array?.map { it.toFloat() } ?: listOf(20F, 80F, 100F)

        array?.forEach {
            //Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        d1 = findViewById(R.id.details1)
        d2 = findViewById(R.id.details2)
        d3 = findViewById(R.id.details3)
        donutChart.donutColors = intArrayOf(
            Color.parseColor("#b3e0dc"),
            Color.parseColor("#81cdc6"),
            Color.parseColor("#4fb9af")
        )
        donutChart.animation.duration = 1000L
        donutChart.animate(donutSet)
        d1.text = "◀  نسبــة الشفـــاء :   ${donutSet[0]}"
        d2.text = "◀  نسبــة الوفيــات :   ${donutSet[1]}"
        d3.text = "◀  نسبــة المصابيــن :   ${donutSet[2]}"
    }
}
