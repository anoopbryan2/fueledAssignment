package com.assignment.fueled.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentResponse (
    var postId : Int= 0,
    var email : String="",
    var name : String="",
    var body : String="",
    var id : Int= 0
) : Parcelable