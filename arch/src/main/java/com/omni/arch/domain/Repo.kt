package com.omni.arch.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repo(
        @Expose @SerializedName("username") val username: String,
        @Expose @SerializedName("name") val name: String,
        @Expose @SerializedName("type") val type: String,
        @Expose @SerializedName("url") val repoUrl: String,
        @Expose @SerializedName("avatar") val avatar: String,
        @Expose @SerializedName("repo") val repo: RepoDesciption
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(RepoDesciption::class.java.classLoader)
    )

    fun getSearchKeys(): String{
        return username.toLowerCase()+" "+name.toLowerCase()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(repoUrl)
        parcel.writeString(avatar)
        parcel.writeParcelable(repo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}