package com.example.myapplication.tabs

import com.example.myapplication.model.*
import retrofit2.Response
import retrofit2.http.GET

interface WalletApi {

    companion object{
        const val WALLET_GET_EARNING="user/wallet/getEarnings"
        const val WALLET_GET_FRIENDLIST="user/wallet/getFriendList"
        const val IS_ONLINE="user/wallet/isOnline"
        const val HOME_DASHBOARD="user/dashBoard"

    }

    @GET(WALLET_GET_EARNING)
    suspend fun getwalletearning() : Response<GetEarningResponse>

    @GET(WALLET_GET_FRIENDLIST)
    suspend fun getfriendlist() : Response<FriendListResponse>

    @GET(IS_ONLINE)
    suspend fun getisonline() : Response<BaseResponse>

    @GET(HOME_DASHBOARD)
    suspend fun getDashboard() : Response<HomeRequestmodel>

}