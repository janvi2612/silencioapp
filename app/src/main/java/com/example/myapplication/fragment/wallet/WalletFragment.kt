package com.example.myapplication.fragment.wallet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBottomSheetDialogsBinding
import com.example.myapplication.databinding.FragmentWalletBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.example.myapplication.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment :  Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    private val viewModel:WalletViewModel by viewModels()
    private lateinit var adapter: WalletAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentWalletBinding.inflate(inflater,container,false)
        setUpi()
        setOnClick()
        setObservers()
        adapter= WalletAdapter()
        binding.recyclerwallet.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerwallet.adapter=adapter
        return binding.root
    }

    private fun setUpi() {
         viewModel.getWalletEarning()
         viewModel.getFriendlistwallet()


        }

    private fun setOnClick(){
        binding.edttextinvitemorefriend.setOnClickListener {
            viewModel.getisonline()
        }
        binding.edttextinvitemorefriend.setOnClickListener {
                viewModel.user.value?.getOrNull(0)?.let {
                    Utils.shareText(
                        requireContext().getString(R.string.invite_string_formatter, it.nickName),
                        requireContext()
                    )
                }

        }
    }

    private fun setObservers() {
        viewModel.verifywalletresponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    response.data?.data?.let {
                        binding.txt1.text = String.format("%,.2f", it.totalWalletAmount)
                        binding.txtwalletyou.text =getString(R.string.wallet_you,
                            it.totalCoinsFromSamples
                        )
                        binding.txtwalletfriend.text= getString(R.string.wallet_friend,
                            it.totalCoinsFromReferal
                        )
                    }

                }
                else -> {}
            }
        })
        viewModel.verifyfriendlist.observe(viewLifecycleOwner, Observer { response ->
        when(response){
            is NetworkResult.Error -> {
                response.message?.snackBar(requireView(), requireContext())
            }
            is NetworkResult.Loading -> {

            }
            is NetworkResult.Success -> {
             response.data?.data?.let { data ->
             adapter.setData(data?.filterNotNull()?: emptyList())

             }

            }
            else -> {}
        }

        })
        viewModel.verifyisonline.observe(viewLifecycleOwner, Observer { response ->
        when(response){
            is NetworkResult.Error -> {
                response.message?.snackBar(requireView(), requireContext())
            }
            is NetworkResult.Loading -> {

            }
            is NetworkResult.Success -> {
                response.data?.message?.snackBar(requireView(),requireContext())
                  viewModel.pingisonline()
            }
            else -> {}

        }

        })
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}









