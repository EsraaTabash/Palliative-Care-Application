package com.example.palliativecareapp.Models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Topic(
    var id: String = "",
    var Logo: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Content: String? = "",
    var Video: String? = "",
    val InfographicUrls: List<String>? = null,
    val Hidden: Boolean = false,
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readBoolean(),
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(Logo)
        parcel.writeString(Name)
        parcel.writeString(Description)
        parcel.writeString(Content)
        parcel.writeString(Video)
        parcel.writeStringList(InfographicUrls)
        parcel.writeBoolean(Hidden)
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
