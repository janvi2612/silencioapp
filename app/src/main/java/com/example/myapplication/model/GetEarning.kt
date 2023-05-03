package com.example.myapplication.model

import com.google.gson.annotations.SerializedName


data class GetEarningResponse(
    @SerializedName("data")
    val data: GetEarning? = null
): BaseResponse()

data class GetEarning(
    val totalWalletAmount : Double? = null,
    val totalCoinsFromSamples : Double? = null,
    val totalCoinsFromReferal : Double? = null
)

