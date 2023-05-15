package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.palliativecareapp.Models.UserRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupPart3 : AppCompatActivity() {
    lateinit var FBFS: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var ref : DatabaseReference
     var userFirstName:String? =""
     var userLastName:String? =""
    lateinit var password:EditText
     lateinit var email:EditText
    lateinit var confirmPassword:EditText
     var userBirthday:String? =""
     var userPhone:String? =""
     var userAddress:String? =""
     var userMiddleName:String? =""
     var userId:Int =2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_part3)
        FBFS = Firebase.firestore
        auth = Firebase.auth
//
//        list = ArrayList()
////        userUid = auth.currentUser?.uid.toString()
        supportActionBar?.hide()
        userId = intent.getIntExtra("id", 2)
         userFirstName = intent.getStringExtra("firstName")
        userMiddleName = intent.getStringExtra("middleName")
         userLastName = intent.getStringExtra("lastName")
        userAddress = intent.getStringExtra("address")
        userPhone = intent.getStringExtra("phone")
        userBirthday = intent.getStringExtra("birthday")
        email = findViewById<EditText>(R.id.signupEmail)
        password = findViewById<EditText>(R.id.signupPassword)
        confirmPassword = findViewById<EditText>(R.id.signupConfirmPassword)
        val signupBtn = findViewById<Button>(R.id.signupButton)

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

            }
        }

//        signUp("$userFirstName $userLastName", email.text.toString(),password.text.toString())


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
        val user = hashMapOf(
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
                val i = Intent(this, Login::class.java)
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
                    val i = Intent(this, Login::class.java)
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
                    val i = Intent(this, Login::class.java)
                    startActivity(i)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Firestore failed.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Error adding document", e)
                }
        }

    }

    private fun createUser(email: String, pass: String,id: Int) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase("$userFirstName $userLastName",email,auth.currentUser?.uid!!,id)
                    Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show()

                    //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("tag", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }




    private fun addUserToDatabase(name:String,email:String,uid:String,id:Int){
        ref = FirebaseDatabase.getInstance().getReference()
        ref.child("user").child(uid).setValue(UserRef(name,email,uid,id))
    }


}