package com.omni.arch.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepoDesciption(
        @Expose @SerializedName("name") val name: String,
        @Expose @SerializedName("description") val description: String,
        @Expose @SerializedName("url") val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoDesciption> {
        override fun createFromParcel(parcel: Parcel): RepoDesciption {
            return RepoDesciption(parcel)
        }

        override fun newArray(size: Int): Array<RepoDesciption?> {
            return arrayOfNulls(size)
        }
    }
}