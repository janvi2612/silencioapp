package com.example.myapplication.repository

import android.app.Application
import com.example.myapplication.model.*
import com.example.myapplication.network.AuthApi
import com.example.myapplication.request.VerifyInviteCodeRequestModel
import com.example.myapplication.response.VerifyInviteCodeResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val application: Application,
    private val api: AuthApi
){
    suspend fun verifyInviteCode(verifyInviteCodeRequestModel: VerifyInviteCodeRequestModel): Response<VerifyInviteCodeResponse> {
        return api.verifyInviteCode(verifyInviteCodeRequestModel)
    }
    suspend fun verifyNumber(verifyNumberRequestModel: VerifyNumberRequestModel):Response<BaseResponse>{
        return api.verifyNumber(verifyNumberRequestModel)
    }
    suspend fun verifyNickName(verifyNickNameRequestModel: VerifyNickNameRequestModel): Response<BaseResponse> {
        return api.verifyNickName(verifyNickNameRequestModel)
    }
    suspend fun signUpWithPhone(registerUserRequestModel: RegisterUserRequestModel): Response<LoginWithPhoneResponseModel> {
        return api.signUpWithPhone(registerUserRequestModel)
    }
    suspend fun loginWithPhone(loginWithPhoneRequestModel: LoginWithPhoneRequestModel): Response<LoginWithPhoneResponseModel> {
        return api.loginWithPhone(loginWithPhoneRequestModel)
    }
    suspend fun editProfile(firstName : String, lastName :String, isFile:Boolean, imageFile : File?,):Response<LoginWithPhoneResponseModel>{

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val mType = "image/jpeg".toMediaTypeOrNull()
        try {
            builder.addFormDataPart("firstName",firstName)
            builder.addFormDataPart("lastName",lastName)
            builder.addFormDataPart("isFile", isFile.toString())
            if(isFile){

                if (imageFile != null && imageFile.exists()) {
                    val fileName =
                        "image${System.currentTimeMillis()}.${imageFile.extension}"
                    builder.addFormDataPart(
                        "picture",
                        fileName,
                        imageFile.asRequestBody(mType)
                    )

                }
            }

        }catch(e: Exception){
            e.printStackTrace()
        }
        val requestBody = builder.build()
        return api.editProfile(requestBody)

    }



    suspend fun deleteAccount(): Response<BaseResponse> {
        return api.deleteAccount()
    }

    suspend fun changePassword(changePassword: ChangePassword): Response<LoginWithPhoneResponseModel>{
        return api.changePassword(changePassword)
    }


}