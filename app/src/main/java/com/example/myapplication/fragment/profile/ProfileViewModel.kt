package com.example.myapplication.fragment.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.*
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(application: Application, private val authDataSource: AuthDataSource,private val userDao: UserDao):BaseViewModel(application)
{

    val user = userDao.getUser()
    private val mContext = application

    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")

    private val _verifyprofileresponse = MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>()
    val verifyprofilersponse : MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>
        get() = _verifyprofileresponse


    private val _verifydeleteaccount = MutableLiveData<NetworkResult<BaseResponse>?>()
    val verifydeleteaccount : MutableLiveData<NetworkResult<BaseResponse>?>
    get() = _verifydeleteaccount


        fun verifyPassword(isFile:Boolean, imageFile : File?){
            viewModelScope.launch {
                _verifyprofileresponse.value = NetworkResult.Loading()
                if(isConnected()){
                    try {
                        val response = authDataSource.editProfile(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            isFile = isFile,
                            imageFile = imageFile


                        )
                        _verifyprofileresponse.value = handleResponse(response = response)
                    }catch (e:Exception){
                        _verifyprofileresponse.value = NetworkResult.Error(
                            context.getString(R.string.something_went_wrong)
                        )

                    }
                }
            }
        }



    fun verifyDelete(){
        viewModelScope.launch {
            if(isConnected()){
                _verifydeleteaccount.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.deleteAccount()
                    _verifydeleteaccount.value = handleResponse(response = response)
                } catch (e: Exception){
                    e.printStackTrace()
                    _verifydeleteaccount.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _verifydeleteaccount.value = NetworkResult.Error(
                    context.getString(R.string.no_internet)
                )
            }
        }
    }

    suspend fun saveUser(userModel: UserModel){
        userDao.insert(userModel)
    }

}