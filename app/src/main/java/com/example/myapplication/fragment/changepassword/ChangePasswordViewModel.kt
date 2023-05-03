package com.example.myapplication.fragment.changepassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.ChangePassword
import com.example.myapplication.model.LoginWithPhoneResponseModel
import com.example.myapplication.model.UserModel
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val application: Application,
    private val userDao: UserDao
):BaseViewModel(application){

    val user = userDao.getUser()
    private val mContext = application

    val oldpassword = MutableStateFlow("")
    val newpassword = MutableStateFlow("")

    private val _verifychangepassword = MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>()
    val verifychangepassword : MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>
        get() = _verifychangepassword



    fun verifychangepassword(){
        viewModelScope.launch {
            if(isConnected()){
                _verifychangepassword.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.changePassword(changePassword = ChangePassword(
                        oldpassword = oldpassword.value,
                        newpassword = newpassword.value
                    ))
                    _verifychangepassword.value = handleResponse(response = response)
                } catch (e: Exception){
                    e.printStackTrace()
                    _verifychangepassword.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _verifychangepassword.value = NetworkResult.Error(
                    context.getString(R.string.no_internet)
                )
            }
        }
    }
    suspend fun saveUser(userModel: UserModel){
        userDao.insert(userModel)
    }





}