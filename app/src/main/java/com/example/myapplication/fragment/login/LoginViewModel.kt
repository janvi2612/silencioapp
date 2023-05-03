package com.example.myapplication.fragment.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.*
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import com.example.myapplication.util.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource,
    private val userDao: UserDao
): BaseViewModel(application) {

    val password = MutableStateFlow("")
    val phoneno = MutableStateFlow("")


    val isvalidlogin = phoneno.combine(password){ p,cp ->
        p.isNotEmpty() && cp.isNotEmpty()
    }

    private val _verifyloginResponse = MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>()
    val verifyloginResponse: MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>?>
        get() = _verifyloginResponse

    fun verifyLogin(countrycode :String){
        viewModelScope.launch {
            _verifyloginResponse.value = NetworkResult.Loading()
            if(isConnected()){
                try {
                    val deviceToken = PrefManager.getString(PrefManager.FIRE_BASE_TOKEN) ?: ""
                    val response = authDataSource.loginWithPhone(
                        loginWithPhoneRequestModel = LoginWithPhoneRequestModel(
                            countryCode = countrycode,
                            phone = phoneno.value,
                            password = password.value,
                            deviceToken = deviceToken
                        )
                    )
              verifyloginResponse.value = handleResponse(response = response)

                }catch (e:Exception){
                   _verifyloginResponse.value = NetworkResult.Error(
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