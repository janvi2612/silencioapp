package com.example.myapplication.fragment.choosepassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.LoginWithPhoneResponseModel
import com.example.myapplication.model.RegisterUserRequestModel
import com.example.myapplication.model.UserModel
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChoosePasswordViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource,
    private val userDao: UserDao
): BaseViewModel(application) {

    val user = userDao.getUser()
    val password = MutableStateFlow("")
    val confirmpassword = MutableStateFlow("")

    val isPasswordValid = password.combine(confirmpassword){ p,cp ->
        p.isNotEmpty() && cp.isNotEmpty()
    }

    private val _verifyPasswordResponse = MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>()
    val verifyPasswordResponse: MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>
        get() = _verifyPasswordResponse


    fun verifyPassword(registerUserRequestModel: RegisterUserRequestModel){
        viewModelScope.launch {
            _verifyPasswordResponse.value = NetworkResult.Loading()
            if(isConnected()){
                try {
                    val response = authDataSource.signUpWithPhone(
                        registerUserRequestModel = registerUserRequestModel.copy (
                            password = password.value,
                            repeatPassword = confirmpassword.value,
                            registrationType = "phone"

                                )
                    )
                    _verifyPasswordResponse.value = handleResponse(response = response)
                }catch (e:Exception){
                    _verifyPasswordResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )

                }
            }
        }
    }

    suspend fun saveUser(userModel: UserModel){
        userDao.insert(userModel)
    }


}