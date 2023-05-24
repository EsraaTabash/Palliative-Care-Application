package com.example.palliativecareapp.Models

import android.os.Parcel
import android.os.Parcelable

data class Topic(
    var id:String = "",
    var Logo: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Content: String? = "",
    var Video: String? = "",
    //var InfographicUrls: List<Float>? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
//        mutableListOf<Float>().apply {
//            parcel.readList(this, Float::class.java.classLoader)
//        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(Logo)
        parcel.writeString(Name)
        parcel.writeString(Description)
        parcel.writeString(Content)
        parcel.writeString(Video)
    //    parcel.writeList(InfographicUrls?.toList())

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Topic> {
        override fun createFromParcel(parcel: Parcel): Topic {
            return Topic(parcel)
        }

        override fun newArray(size: Int): Array<Topic?> {
            return arrayOfNulls(size)
        }
    }
}
