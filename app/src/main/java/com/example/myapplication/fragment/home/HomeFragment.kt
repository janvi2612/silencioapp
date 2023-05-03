package com.example.myapplication.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.fragment.wallet.WalletViewModel
import com.example.myapplication.snackBar
import com.example.myapplication.util.Constant1
import com.example.myapplication.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var sideMenuBroadcaster:LocalBroadcastManager
    private lateinit var bottomNavBroadcaster:LocalBroadcastManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        setUpi()
        setUpUI()
        setOnClicks()
        setobserver()
        return binding.root
    }

    private fun setUpi() {
        viewModel.getWalletEarning()
        viewModel.getdashboard()
    }


    private fun setobserver() {
        viewModel.verifywalletresponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    response.data?.data?.let {
                        binding.text.text = String.format("%,.2f", it.totalWalletAmount)
                    }

                }
                else -> {}
            }
        })

    }

    private fun setUpUI() {
        sideMenuBroadcaster = LocalBroadcastManager.getInstance(requireContext())
        bottomNavBroadcaster = LocalBroadcastManager.getInstance(requireContext())
    }


    private fun setOnClicks() {
        binding.toolbar.setOnClickListener {
            sideMenuBroadcaster.sendBroadcast(
                Intent(Constant1.BRODCAST_RECIVER.SIDE_MENU)
            )

        }
    }
}