package com.example.myapplication.fragment.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentChangePasswordBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.example.myapplication.util.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChangePasswordFragment : BottomSheetDialogFragment() {
      private  var _binding : FragmentChangePasswordBinding?= null
     private val binding get() = _binding!!

    private val viewModel: ChangePasswordViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        setOnClicks()
        setObservers()
        return binding.root
    }

    private fun setOnClicks() {
        binding.btnContinue.setOnClickListener {
            viewModel.verifychangepassword()
        }
    }
    private fun setObservers(){
        viewModel.verifychangepassword.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is NetworkResult.Error ->{
                    response.message?.snackBar(requireView(),requireContext())
                }
                is NetworkResult.Idle ->{

                }
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Success ->{
                    response.data?.data?.let {
                        lifecycleScope.launch{
                            with(Dispatchers.IO){
                                viewModel.saveUser(it.copy(uuid = 1))
                                PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                                PrefManager.setString(PrefManager.ACCESS_TOKEN, it.authToken)

                            }

                        }
                    }
                }


                else -> {}
            }

        })
    }



}