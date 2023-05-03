package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterUserRequestModel(
    val firstName: String? = null,
    val lastName: String? = null,
    val nickName: String? = null,
    val password: String? = null,
    val repeatPassword: String? = null,
    val countryCode: String? = null,
    val phone: String? = null,
    val registrationType: String? = null,
    val referreId: String? = null,
    val deviceToken: String? = null,
): Parcelable