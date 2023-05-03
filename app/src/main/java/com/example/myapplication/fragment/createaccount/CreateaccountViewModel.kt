package com.example.myapplication.fragment.createaccount

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.model.BaseResponse
import com.example.myapplication.model.LoginWithPhoneResponseModel
import com.example.myapplication.model.RegisterUserRequestModel
import com.example.myapplication.model.VerifyNickNameRequestModel
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateaccountViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource
    ):BaseViewModel(application) {

    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val nickName = MutableStateFlow("")

    val isNickNameValid = firstName.combine(lastName) { fn, ln ->
        fn.isNotEmpty() && ln.isNotEmpty()
    }.combine(nickName) { name, nickname ->
        name && nickname.isNotEmpty()
    }

    private val _verifyNickNameResponse = MutableLiveData<NetworkResult<BaseResponse>?>()
    val verifyNickNameResponse: MutableLiveData<NetworkResult<BaseResponse>?>
        get() = _verifyNickNameResponse


    fun verifyNickName() {
        viewModelScope.launch {
            _verifyNickNameResponse.value = NetworkResult.Loading()
            if (isConnected()) {
                try {
                    val response = authDataSource.verifyNickName(
                        VerifyNickNameRequestModel(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            nickName = nickName.value
                        )
                    )
                    _verifyNickNameResponse.value = handleResponse(response = response)
                } catch (e: Exception) {
                    _verifyNickNameResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )

                }
            }
        }
    }
}
