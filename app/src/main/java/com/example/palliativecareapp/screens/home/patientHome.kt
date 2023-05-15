package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.palliativecareapp.screens.chat.DisplayUsersActivity
import com.example.palliativecareapp.screens.chat.PatientChat

class PatientHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_home)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.message -> startActivity(Intent(this,DisplayUsersActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}