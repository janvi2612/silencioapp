package com.example.myapplication.fragment.wallet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.UserDao
import com.example.myapplication.model.*
import com.example.myapplication.repository.WalletRepository
import com.example.myapplication.util.BaseViewModel
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor
    (private val repository: WalletRepository,application: Application,private val userDao: UserDao)
    :BaseViewModel(application){


        val user = userDao.getUser()


        private val mContext = application
        private val _verifywalletresponse = MutableLiveData<NetworkResult<GetEarningResponse>?>()
        val verifywalletresponse :MutableLiveData<NetworkResult<GetEarningResponse>?>
        get() = _verifywalletresponse

        private val _verifyfriendlist = MutableLiveData<NetworkResult<FriendListResponse>?>()
        val verifyfriendlist : MutableLiveData<NetworkResult<FriendListResponse>?>
          get() = _verifyfriendlist

    private val _verifyisonline = MutableLiveData<NetworkResult<BaseResponse>?>()
    val verifyisonline : MutableLiveData<NetworkResult<BaseResponse>?>
        get() = _verifyisonline



    init {
        Timber.e("init")
        getWalletEarning()
    }


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

    fun getFriendlistwallet(){
        viewModelScope.launch {
            _verifyfriendlist.value=NetworkResult.Loading()
            if(isConnected()){
                val response3 : Response<FriendListResponse> = repository.getfriendlist()
                _verifyfriendlist.value = handleResponse(response3)
            }
            else{
                _verifyfriendlist.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }
        }

    }
    fun getisonline(){
        viewModelScope.launch {
            _verifyisonline.value=NetworkResult.Loading()
            if(isConnected()){
                val response3 : Response<BaseResponse> = repository.getisonline()
               _verifyisonline.value = handleResponse(response3)
            }
            else{
               _verifyisonline.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }
        }

    }

    suspend fun saveUser(userModel: UserModel){
        userDao.insert(userModel)
    }

    fun pingisonline(){
        _verifyisonline.value=NetworkResult.Idle()
    }

    }