package com.example.palliativecareapp.screens.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.palliativecareapp.Login
import com.example.palliativecareapp.Models.UserRef
import com.example.palliativecareapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MysignupPart3Activity : AppCompatActivity() {
    lateinit var signupEmail :EditText
    lateinit var signupPassword :EditText
    lateinit var signupConfirmPassword :EditText
    lateinit var signupButton : Button

    private lateinit var firestordb :FirebaseFirestore
    private lateinit var realtimedb:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    private var fname:String? =""
    private var mname:String?= ""
    private var lname:String?=""
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var confirmPassword:String
    private var uid:String?=""

    private var birthDate:String?=""
    private var phone:String?=""
    private var address:String?=""
    private var id:Int = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_part3_my)
//         signupEmail=findViewById<EditText>(R.id.signupEmail)
//        signupPassword=findViewById<EditText>(R.id.signupPassword)
//        signupConfirmPassword=findViewById<EditText>(R.id.signupConfirmPassword)
//        signupButton=findViewById(R.id.signupButton)

        firestordb = Firebase.firestore
        realtimedb = FirebaseDatabase.getInstance()
        auth = Firebase.auth

//        email = signupEmail.text.toString()
//        password = signupPassword.text.toString()
//        confirmPassword = signupConfirmPassword.text.toString()

        id = intent.getIntExtra("id", 2)
        fname = intent.getStringExtra("firstName")
        mname = intent.getStringExtra("middleName")
        lname = intent.getStringExtra("lastName")
        address = intent.getStringExtra("address")
        phone = intent.getStringExtra("phone")
        birthDate = intent.getStringExtra("birthday")
        Log.e("aa","$fname,$mname,$lname,$address,$phone")



//        signupButton.setOnClickListener {
//            Log.e("aa","the id is $id")
//            Log.e("aa"," email and pass and id$email $password $id")
//            if (signupEmail.text.isEmpty() || signupPassword.text.isEmpty()){
//                Toast.makeText(this, "email or passwor is empty", Toast.LENGTH_SHORT).show()
//            }else{
//                Log.e("aa","in else email and pass and id$email $password $id")
//                createUser(
//                    email,
//                    password,
//                    id)
//            }
//            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()


//            if (signupEmail.text.toString().isEmpty()) {
//                signupEmail.error = "الرجاء تعبئة هذا الحقل"
//                signupEmail.requestFocus()
//            } else if (signupPassword.text.toString().isEmpty()) {
//                signupPassword.error = "الرجاء تعبئة هذا الحقل"
//                signupPassword.requestFocus()
//            } else if (signupConfirmPassword.text.toString().isEmpty()) {
//                signupConfirmPassword.error = "الرجاء تعبئة هذا الحقل"
//                signupConfirmPassword.requestFocus()
//            } else if (signupPassword.text.toString() != signupConfirmPassword.text.toString()) {
//                signupConfirmPassword.error = "الرجاء التاكد من كتابة كلمة المرور بشكل صحيح"
//                signupConfirmPassword.requestFocus()
//            } else {


//                auth.createUserWithEmailAndPassword(
//                    signupEmail.text.toString(),
//                    signupPassword.text.toString()
//                )
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this, "Authentication success1.", Toast.LENGTH_SHORT)
//                                .show()
//                            addUserToDatabase(
//                                "$fname $lname",
//                                signupEmail.text.toString(),
//                                auth.currentUser?.uid!!,
//                                id
//                            )
//                            addNewUser(
//                                id,
//                                auth.currentUser?.uid!!,
//                                fname,
//                                mname,
//                                lname,
//                                address,
//                                phone,
//                                birthDate,
//                                signupEmail.text.toString(),
//                                signupPassword.text.toString()
//                            )
//                            Toast.makeText(this, "Authentication success2.", Toast.LENGTH_SHORT)
//                                .show()
//                            //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
//                        } else {
//                            Log.d("tag", "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }

//                createUser(signupEmail.text.toString(),signupPassword.text.toString(),id)

//            }
//        }
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
        firestordb.collection("users")
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
            firestordb.collection("patient")
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
            firestordb.collection("doctor")
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


    private fun createUser(myemail: String, pass: String,id: Int) {
        auth.
        createUserWithEmailAndPassword(
            myemail,
            pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Authentication success1.", Toast.LENGTH_SHORT).show()
                    addUserToDatabase("$fname $lname",myemail,auth.currentUser?.uid!!,id)
                    addNewUser(id,auth.currentUser?.uid!!,fname,mname,lname,address,phone,birthDate,myemail,pass)
                    Toast.makeText(this, "Authentication success2.", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this, userUid, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("tag", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }




    private fun addUserToDatabase(name:String,email:String,uid:String,id:Int){
        realtimedb = FirebaseDatabase.getInstance()
        realtimedb.getReference().child("user").child(uid).setValue(UserRef(name,email,uid,id))
    }



}