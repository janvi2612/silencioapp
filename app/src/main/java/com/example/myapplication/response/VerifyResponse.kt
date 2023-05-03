package com.example.myapplication.response

import com.example.myapplication.model.BaseResponse
import com.google.gson.annotations.SerializedName


data class VerifyInviteCodeResponse(
    @SerializedName("data")
    val data: VerifyInviteCodeResponseModel? = null
): BaseResponse()

data class VerifyInviteCodeResponseModel(
    @SerializedName("_id")
    val id: String? = null
)