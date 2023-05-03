package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class VerifyInviteCode(
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
data class Data(
    @SerializedName("appleId")
    var appleId: String?,
    @SerializedName("authToken")
    var authToken: String?,
    @SerializedName("avgTime")
    var avgTime: Int?,
    @SerializedName("countryCode")
    var countryCode: String?,
    @SerializedName("createdAt")
    var createdAt: Long?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("inviteCode")
    var inviteCode: String?,
    @SerializedName("isActive")
    var isActive: Boolean?,
    @SerializedName("isDeleted")
    var isDeleted: Boolean?,
    @SerializedName("lastLogin")
    var lastLogin: Long?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("nickName")
    var nickName: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("profileImg")
    var profileImg: String?,
    @SerializedName("recordingCount")
    var recordingCount: Int?,
    @SerializedName("recordingTime")
    var recordingTime: Int?,
    @SerializedName("referredBy")
    var referredBy: Any?,
    @SerializedName("registrationType")
    var registrationType: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("updatedAt")
    var updatedAt: Long?,
    @SerializedName("walletBalance")
    var walletBalance: Double?
)