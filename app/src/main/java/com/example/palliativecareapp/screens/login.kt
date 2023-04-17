package com.example.palliativecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecareapp.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var list: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        firestore = Firebase.firestore
        list = ArrayList()
        supportActionBar?.hide()
        val email = findViewById<EditText>(R.id.signinEmail)
        val password = findViewById<EditText>(R.id.signinPassword)
        val signupBtn = findViewById<TextView>(R.id.signupButton)
        val signinBtn = findViewById<Button>(R.id.signinButton)
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                list.clear()
                for (document in result) {
                    list.add(
                        User(
                            email = document.getString("email"),
                            password =  document.getString("password"),
                        )
                    )
                }
                list.forEach{
                    Toast.makeText(this,"email : ${it.email}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"password : ${it.password}", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting documents.", exception)
            }



        signinBtn.setOnClickListener {
           if (email.text.toString().isEmpty()) {
           email.error = "الرجاء تعبئة هذا الحقل"
           email.requestFocus()
       }else if (password.text.toString().isEmpty()) {
           password.error = "الرجاء تعبئة هذا الحقل"
           password.requestFocus()
       }else if(list.filter { it.email == email.text.toString() }.size >1){
               email.error = "يوجد خطا في الايميل"
               email.requestFocus()
       }else if(list.filter { it.password == password.text.toString() }.size >1){
               password.error = "يوجد خطا في كلمة المرور"
               password.requestFocus()
       }else if(list.filter { it.email == email.text.toString() }.isEmpty()){
               email.error = "هذا ايميل غير مسجل من قبل"
               email.requestFocus()
       }else if(list.filter { it.password == password.text.toString() }.isEmpty()) {
               password.error = "كلمة المرور خاطئة"
               password.requestFocus()
           }else{
           loginUser(email.text.toString(),password.text.toString())
           }
       }

        signupBtn.setOnClickListener {
            val i = Intent (this,SignupType::class.java)
            startActivity(i)
        }

    }

     fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                //val user = auth.currentUser
                Log.d("tag", "signinUser:Success")
                Toast.makeText(this, "Authentication sucess.", Toast.LENGTH_SHORT).show()
                var i = Intent(this, Home::class.java)
                startActivity(i)
            } else {
                Log.d("tag", "signinUserWithEmail:failure", task.exception)
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }