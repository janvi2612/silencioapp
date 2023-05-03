package com.example.myapplication.fragment.registerphoneno

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.model.BaseResponse
import com.example.myapplication.model.VerifyNumberRequestModel
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.response.VerifyInviteCodeResponse
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterphnViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource
): BaseViewModel(application) {

    val phoneNumber = MutableStateFlow("")

    val isPhoneNumberValid = phoneNumber.map {
        it.isNotEmpty()
    }

    private val _verifyNumberResponse = MutableLiveData<NetworkResult<BaseResponse>?>()
    val verifyNumberResponse: MutableLiveData<NetworkResult<BaseResponse>?>
        get() = _verifyNumberResponse

    fun verifyNumber(countryCode: String){
        viewModelScope.launch {
            _verifyNumberResponse.value = NetworkResult.Loading()
            if(isConnected()){
                try {
                    val response = authDataSource.verifyNumber(
                        VerifyNumberRequestModel(
                            countryCode = countryCode,
                            phone = phoneNumber.value
                        )
                    )
                    _verifyNumberResponse.value = handleResponse(response = response)
                } catch (e: Exception){
                    e.printStackTrace()
                    _verifyNumberResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _verifyNumberResponse.value = NetworkResult.Error(
                    context.getString(R.string.no_internet)
                )
            }
        }
    }
}





