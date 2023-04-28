package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignupPart1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_part1)
        supportActionBar?.hide()
        val userId = intent.getIntExtra("id", 2)
        //Toast.makeText(this, userId.toString(), Toast.LENGTH_SHORT).show()
        var firstName = findViewById<EditText>(R.id.signupFirstName)
        var middleName = findViewById<EditText>(R.id.signupMiddleName)
        var lastName = findViewById<EditText>(R.id.signupLastName)
        var nextBtn = findViewById<Button>(R.id.nextButton)
        nextBtn.setOnClickListener {
            if (firstName.text.toString().isEmpty()) {
                firstName.error = "الرجاء تعبئة هذا الحقل"
                firstName.requestFocus()
            } else if (middleName.text.toString().isEmpty()) {
                middleName.error = "الرجاء تعبئة هذا الحقل"
                middleName.requestFocus()
            } else if (lastName.text.toString().isEmpty()) {
                lastName.error = "الرجاء تعبئة هذا الحقل"
                lastName.requestFocus()
            } else {
                val i = Intent(this, SignupPart2::class.java)
                i.putExtra("id",userId)
                i.putExtra("firstName", firstName.text.toString())
                i.putExtra("middleName", middleName.text.toString())
                i.putExtra("lastName", lastName.text.toString())
                //Toast.makeText(this, "${firstName.text} ${middleName.text} ${lastName.text}", Toast.LENGTH_SHORT).show()
                startActivity(i)
            }
        }

    }
}