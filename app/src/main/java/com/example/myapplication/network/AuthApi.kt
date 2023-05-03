package com.example.myapplication.network

import com.example.myapplication.model.*
import com.example.myapplication.request.VerifyInviteCodeRequestModel
import com.example.myapplication.response.VerifyInviteCodeResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    companion object{
        const val VERIFY_CODE="user/auth/verifyInviteCode"
        const val VERIFY_NUMBER = "user/auth/validatePhone"
        const val VERIFY_NICK_NAME = "user/auth/nickNameVerify"
        const val SIGNUP_WITH_PHONE = "user/auth/registerUserWithPhone"
        const val VERIFY_LOGIN_WITH_PHONE = "user/auth/loginUser"
        const val EDIT_PROFILE = "user/auth/editProfile"
        const val DELETE_ACCOUNT = "user/auth/deleteAccount"
        const val CHANGE_PASSWORD="user/auth/changePassword"

    }

 @POST(VERIFY_CODE)
 suspend fun verifyInviteCode(
     @Body verifyInviteCodeRequestModel: VerifyInviteCodeRequestModel
 ):Response<VerifyInviteCodeResponse>

    @POST(VERIFY_NUMBER)
    suspend fun verifyNumber(
        @Body verifyNumberRequestModel: VerifyNumberRequestModel
    ): Response<BaseResponse>

    @POST(VERIFY_NICK_NAME)
    suspend fun verifyNickName(
        @Body verifyNickNameRequestModel: VerifyNickNameRequestModel
    ): Response<BaseResponse>

    @POST(SIGNUP_WITH_PHONE)
    suspend fun signUpWithPhone(
        @Body registerUserRequestModel: RegisterUserRequestModel
    ): Response<LoginWithPhoneResponseModel>

    @POST(VERIFY_LOGIN_WITH_PHONE)
    suspend fun loginWithPhone(
        @Body loginWithPhoneRequestModel: LoginWithPhoneRequestModel
    ): Response<LoginWithPhoneResponseModel>

    @POST(CHANGE_PASSWORD)
    suspend fun changePassword(
        @Body changePasswordRequestModel: ChangePassword
    ) : Response<LoginWithPhoneResponseModel>



    @PATCH(EDIT_PROFILE)
    suspend fun editProfile(
        @Body requestBody: RequestBody
    ): Response<LoginWithPhoneResponseModel>


    @PATCH(DELETE_ACCOUNT)
    suspend fun deleteAccount(): Response<BaseResponse>





}