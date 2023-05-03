package com.example.myapplication.fragment.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.GetEarningResponse
import com.example.myapplication.model.HomeRequestmodel
import com.example.myapplication.repository.WalletRepository
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor
    (private val repository: WalletRepository, application: Application, private val userDao: UserDao)
    : BaseViewModel(application) {


    private val mContext = application
    private val _verifywalletresponse = MutableLiveData<NetworkResult<GetEarningResponse>?>()
    val verifywalletresponse: MutableLiveData<NetworkResult<GetEarningResponse>?>
        get() = _verifywalletresponse

    private val _verifyhomeresponse = MutableLiveData<NetworkResult<HomeRequestmodel>?>()
    val verifyhomeresponse: MutableLiveData<NetworkResult<HomeRequestmodel>?>
        get() = _verifyhomeresponse


    fun getWalletEarning() {

        viewModelScope.launch {
            _verifywalletresponse.value=NetworkResult.Loading()
            if(isConnected()){
                val response3 : Response<GetEarningResponse> = repository.getwalletearning()
                _verifywalletresponse.value = handleResponse(response3)
            }
            else{
                _verifywalletresponse.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }
        }


    }
    fun getdashboard() {

        viewModelScope.launch {
            _verifyhomeresponse.value=NetworkResult.Loading()
            if(isConnected()){
                val response3 : Response<HomeRequestmodel> = repository.getdashboard()
                _verifyhomeresponse.value = handleResponse(response3)
            }
            else{
                _verifyhomeresponse.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }
        }

    }


}