package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePassword(
   val oldpassword:String,
   val newpassword:String
   ):Parcelable
