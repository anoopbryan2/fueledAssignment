package com.assignment.fueled.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostResponse (
        val userId : Int,
        val id : Int,
        val title : String,
        val body : String
) : Parcelable