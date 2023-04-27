package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupPart3 : AppCompatActivity() {
    lateinit var FBFS: FirebaseFirestore
    lateinit var list: ArrayList<User>
    lateinit var auth: FirebaseAuth
    lateinit var userUid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_part3)
        FBFS = Firebase.firestore
        auth = Firebase.auth
        list = ArrayList()
        userUid = auth.currentUser?.uid.toString()
        supportActionBar?.hide()
        val userId = intent.getIntExtra("id", 2)
        val userFirstName = intent.getStringExtra("firstName")
        val userMiddleName = intent.getStringExtra("middleName")
        val userLastName = intent.getStringExtra("lastName")
        val userAddress = intent.getStringExtra("address")
        val userPhone = intent.getStringExtra("phone")
        val userBirthday = intent.getStringExtra("birthday")
        var email = findViewById<EditText>(R.id.signupEmail)
        var password = findViewById<EditText>(R.id.signupPassword)
        var confirmPassword = findViewById<EditText>(R.id.signupConfirmPassword)
        var signupBtn = findViewById<Button>(R.id.signupButton)

        signupBtn.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.error = "الرجاء تعبئة هذا الحقل"
                email.requestFocus()
            } else if (password.text.toString().isEmpty()) {
                password.error = "الرجاء تعبئة هذا الحقل"
                password.requestFocus()
            } else if (confirmPassword.text.toString().isEmpty()) {
                confirmPassword.error = "الرجاء تعبئة هذا الحقل"
                confirmPassword.requestFocus()
            } else if (password.text.toString() != confirmPassword.text.toString()) {
                confirmPassword.error = "الرجاء التاكد من كتابة كلمة المرور بشكل صحيح"
                confirmPassword.requestFocus()
            } else {
                createUser(email.text.toString(), password.text.toString())
                Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                addNewUser(
                    userId,
                    userUid,
                    userFirstName,
                    userMiddleName,
                    userLastName,
                    userAddress,
                    userPhone,
                    userBirthday,
                    email.text.toString(),
                    password.text.toString()
                )
            }
        }

    }

    private fun addNewUser(
        id: Int?,
        uid: String?,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        address: String?,
        phone: String?,
        birthday: String?,
        email: String?,
        password: String?
    ) {
        var user = hashMapOf(
            "id" to id,
                "uid" to uid,
            "firstName" to firstName,
            "middleName" to middleName,
            "lastName" to lastName,
            "address" to address,
            "phone" to phone,
            "birthday" to birthday,
            "email" to email,
            "password" to password
        )
        FBFS.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Firestore sucess.", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                var i = Intent(this, Login::class.java)
                startActivity(i)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Firestore failed.", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Error adding document", e)
            }

        if (id == 0){
            FBFS.collection("patient")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "Firestore sucess.", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                    var i = Intent(this, Login::class.java)
                    startActivity(i)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Firestore failed.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Error adding document", e)
                }
        }else if (id == 1){
            FBFS.collection("doctor")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "Firestore sucess.", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                    var i = Intent(this, Login::class.java)
                    startActivity(i)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Firestore failed.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Error adding document", e)
                }
        }

    }

    private fun createUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("tag", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}