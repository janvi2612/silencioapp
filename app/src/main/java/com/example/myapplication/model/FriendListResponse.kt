package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class FriendListResponse (
    val data: List<FriendListModel>? = null
):BaseResponse()
data class FriendListModel(
    val amount: Double? = null,
    @SerializedName("friendFirstName")
    val friendFirstName: String? = null,
    @SerializedName("friendId")
    val friendId: FriendId? = null,
    @SerializedName("friendLastName")
    val friendLastName: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("isFriendDeleted")
    val isFriendDeleted: Boolean? = null,
    @SerializedName("profilePic")
    val profilePic: String? = null,
    @SerializedName("timeStamp")
    val timeStamp: Long? = null
)
data class FriendId(
    @SerializedName("appleId")
    val appleId: Any? = null,
    @SerializedName("authToken")
    val authToken: String? = null,
    @SerializedName("avgTime")
    val avgTime: Double? = null,
    @SerializedName("countryCode")
    val countryCode: String? = null,
    @SerializedName("createdAt")
    val createdAt: Double? = null,
    @SerializedName("deviceToken")
    val deviceToken: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("id")
    val id: String,
    @SerializedName("inviteCode")
    val inviteCode: String? = null,
    @SerializedName("isActive")
    val isActive: Boolean? = null,
    @SerializedName("isDeleted")
    val isDeleted: Boolean? = null,
    @SerializedName("lastLogin")
    val lastLogin: Long? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("nickName")
    val nickName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("profileImg")
    val profileImg: String? = null,
    @SerializedName("recordingCount")
    val recordingCount: Double? = null,
    @SerializedName("recordingTime")
    val recordingTime: Double? = null,
    @SerializedName("referredBy")
    val referredBy: String? = null,
    @SerializedName("registrationType")
    val registrationType: String? = null,
    @SerializedName("resetPasswordCode")
    val resetPasswordCode: Any? = null,
    @SerializedName("resetPasswordToken")
    val resetPasswordToken: Any? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: Long? = null,
    @SerializedName("walletBalance")
    val walletBalance: Double? = null
)



















