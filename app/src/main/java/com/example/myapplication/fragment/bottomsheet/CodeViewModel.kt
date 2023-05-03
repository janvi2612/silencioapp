package com.example.myapplication.fragment.bottomsheet

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.request.VerifyInviteCodeRequestModel
import com.example.myapplication.response.VerifyInviteCodeResponse
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource
): BaseViewModel(application) {


    val code = MutableStateFlow("")

    val isCodeValid = code.map {
        it.isNotEmpty()
    }

    private val _codeResponse = MutableLiveData<NetworkResult<VerifyInviteCodeResponse>?>()
    val codeResponse : LiveData<NetworkResult<VerifyInviteCodeResponse>?>
        get() = _codeResponse


    fun submitCode(){
        viewModelScope.launch {
            if(isConnected()){
                _codeResponse.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.verifyInviteCode(VerifyInviteCodeRequestModel(code.value))
                    _codeResponse.value = handleResponse(response = response)
                } catch (e: Exception){
                    e.printStackTrace()
                    _codeResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _codeResponse.value = NetworkResult.Error(
                    context.getString(R.string.no_internet)
                )
            }
        }
    }
}