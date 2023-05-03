package com.example.myapplication.model

data class LoginWithPhoneRequestModel(
    val countryCode: String,
    val phone: String,
    val password: String,
    val deviceToken: String
)
