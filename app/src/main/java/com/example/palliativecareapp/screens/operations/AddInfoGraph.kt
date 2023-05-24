package com.example.palliativecareapp.screens.operations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.palliativecareapp.DoctorHome
import com.example.palliativecareapp.R

class AddInfoGraph : AppCompatActivity() {
    lateinit var num1:EditText
    lateinit var num2:EditText
    lateinit var num3:EditText
//    lateinit var t1:EditText
//    lateinit var t2:EditText
//    lateinit var t3:EditText
    lateinit var saveBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info_graph)
        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
        num3 = findViewById(R.id.num3)
//        t1 = findViewById(R.id.details1input)
//        t2 = findViewById(R.id.details2input)
//        t3 = findViewById(R.id.details3input)
        saveBtn = findViewById(R.id.saveinfo)
        saveBtn.setOnClickListener {
            val i = Intent(this,AddTopic::class.java)
            i.putExtra("num1",num1.text)
            i.putExtra("num2",num2.text)
            i.putExtra("num3",num3.text)
//            i.putExtra("t1",t1.text)
//            i.putExtra("t2",t2.text)
//            i.putExtra("t3",t3.text)
            startActivity(i)
        }

    }
}