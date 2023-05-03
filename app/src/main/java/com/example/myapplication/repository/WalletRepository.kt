package com.example.myapplication.repository


import com.example.myapplication.model.BaseResponse
import com.example.myapplication.model.FriendListResponse
import com.example.myapplication.model.GetEarningResponse
import com.example.myapplication.model.HomeRequestmodel
import com.example.myapplication.tabs.WalletApi
import retrofit2.Response
import javax.inject.Inject

class WalletRepository @Inject constructor(

    private val apiInterface: WalletApi
) {
    suspend fun getwalletearning(): Response<GetEarningResponse> {
        return apiInterface.getwalletearning()
    }
    suspend fun getfriendlist(): Response<FriendListResponse> {
        return apiInterface.getfriendlist()
    }
    suspend fun getisonline(): Response<BaseResponse> {
        return apiInterface.getisonline()
    }
    suspend fun getdashboard(): Response<HomeRequestmodel> {
        return apiInterface.getDashboard()
    }





}