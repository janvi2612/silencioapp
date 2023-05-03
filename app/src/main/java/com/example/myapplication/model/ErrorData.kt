package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("errorData")
    var errorData: ErrorDataX?,
    @SerializedName("isError")
    var isError: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?
)

