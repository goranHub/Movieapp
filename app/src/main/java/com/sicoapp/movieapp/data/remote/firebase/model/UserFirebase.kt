package com.sicoapp.movieapp.data.remote.firebase.model

import android.os.Parcel
import android.os.Parcelable

data class UserFirebase(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val movieId: String = "",
    val movieRating: String = "",
    val fcmToken: String = ""
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeString(movieId)
        writeString(movieRating)
        writeString(fcmToken)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserFirebase> = object : Parcelable.Creator<UserFirebase> {
            override fun createFromParcel(source: Parcel): UserFirebase = UserFirebase(source)
            override fun newArray(size: Int): Array<UserFirebase?> = arrayOfNulls(size)
        }
    }
}