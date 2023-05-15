package com.example.palliativecareapp

import com.example.palliativecareapp.Models.Topic

interface TopicLoadListener{
    fun onTopicLodSuccess(topicModelList:List<Topic>)
    fun onTopicLodFailed(message:String?)

}