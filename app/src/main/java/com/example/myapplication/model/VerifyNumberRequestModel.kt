package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyNumberRequestModel (
    val countryCode:String,
    val phone:String,
    val code:String?=null
):Parcelable