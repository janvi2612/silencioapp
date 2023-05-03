package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class LoginWithPhoneResponseModel(
    @SerializedName("data")
    val data: UserModel? = null
): BaseResponse()

@Entity(tableName = "user")
@Parcelize
data class UserModel(
    @PrimaryKey(autoGenerate = false)
    val uuid: Int = 1,
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val nickName: String? = null,
    val type: String? = null,
    val registrationType: String? = null,
    val countryCode: String? = null,
    val phone: String? = null,
    val walletBalance: String? = null,
    val recordingTime: String? = null,
    val recordingCount: String? = null,
    val avgTime: String? = null,
    val lastLogin: String? = null,
    val authToken: String? = null,
    val inviteCode: String? = null,
    val profileImg: String? = null
): Parcelable