package com.example.palliativecareapp.Models

data class User(
    var id: Int? ,
    var uid: String? = "" ,
    var firstName: String? = "",
   var middleName: String? = "",
    var lastName: String? = "",
    var address: String? = "",
    var phone: String? = "",
    var birthday: String? = "",
    var email: String?,
    var password: String?,
    )