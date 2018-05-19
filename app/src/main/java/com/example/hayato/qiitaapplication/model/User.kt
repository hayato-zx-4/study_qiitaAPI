package com.example.hayato.qiitaapplication.model

import android.os.Parcel
import android.os.Parcelable

data class User(
//        val description: String,
//        val facebook_id: String,
//        val followees_count: Int,
//        val followers_count: Int,
//        val github_login_name: String,
        val id: String,
//        val items_count: Int,
//        val linkedin_id: String,
//        val location: String,
        val name: String,
//        val organization: String,
//        val permanent_id: Int,
        val profileImageUrl: String
//        val twitter_screen_name: String,
//        val website_url: String
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): User = source.run { User(readString(), readString(), readString()) }
        }
    }

    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeString(id)
            writeString(name)
            writeString(profileImageUrl)
        }
    }

}