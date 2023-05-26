package com.example.palliativecareapp.Models

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyAnalytics {

    companion object{
        val analytics = Firebase.analytics
        val db = Firebase.firestore
        val storage= Firebase.storage

        fun screenTrack(screenClass:String, screenName:String){


            analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
                param(FirebaseAnalytics.Param.SCREEN_CLASS,screenClass)
                param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            }
        }

        fun clickTrack(){
            analytics.logEvent("clickTracking"){
                param("makeTrack","click")

            }
        }

    }

//    override fun onResume() {
//        super.onResume()
//        openTime = System.currentTimeMillis()
//        Log.e("byn","time to open : "+openTime.toString())
//    }
//
//    override fun onPause() {
//        super.onPause()
//        closeTime = System.currentTimeMillis()
//        Log.e("byn","time to close : "+ closeTime.toString())
//
//        val timeSpend = (closeTime - openTime) / 1000
////        Log.e("byn","time spend = ${timeSpend/1000} s")
//        Log.e("byn","time spend in home = ${timeSpend} s")
//
//        val time = hashMapOf(
//            "pageName" to "Home",
//            "time" to "$timeSpend s",
//            "userId" to "user1"
//        )
//        db.collection("screenTime").add(time).addOnSuccessListener {
//            Log.e("byn","home screen time added successfully")
//        }.addOnFailureListener {
//            Log.e("byn","failed to add home screen time")
//        }
//
//    }


}