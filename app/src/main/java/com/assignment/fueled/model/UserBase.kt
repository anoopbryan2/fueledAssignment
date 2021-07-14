package com.assignment.fueled.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//
//import Address
//import Company
//
@Parcelize
data class UserBase  (
    var id : Int = 0,
    var name : String = "",
    var username : String= "",
    var email : String= "",
    var address : Address? = null,
    var phone : String = "",
    var website : String= "",
    var company : Company? = null,
    var score : Int = 0
) : Parcelable, Comparable<UserBase>{
    override fun compareTo(other: UserBase): Int {
        if(score<other.score){
            return 1
        }else if(score<other.score){
           return 0
        }else{
            return -1
        }
    }

}