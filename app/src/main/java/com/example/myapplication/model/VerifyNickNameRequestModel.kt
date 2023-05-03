package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyNickNameRequestModel(
    val firstName:String,
    val lastName:String,
    val nickName:String
):Parcelable








