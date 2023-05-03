package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeRequestmodel(
    val userCount: Double,
    val userUploads: Double,
    val hours: Double,
    val noiseCoins:Double
):Parcelable