package com.example.myapplication.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.example.myapplication.util.PrefManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private var isPhoneValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        binding.viewModels = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setOnClicks()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setOnClicks() {
        binding.btnclose.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btncontinue.setOnClickListener {
            if (isPhoneValid) {
                viewModel.verifyLogin(binding.edtcountrycodepicker.selectedCountryCodeWithPlus)
            }
        }

    }
    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.isvalidlogin.collect {
                isPhoneValid = it
                binding.btncontinue.isEnabled = it
              // binding.btncontinue.isClickable = it
            }
        }
        viewModel.verifyloginResponse.observe(viewLifecycleOwner){ response ->
            when(response){

                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                  response.data?.data?.let {
                      lifecycleScope.launch{
                          with(Dispatchers.IO){
                              viewModel.saveUser(it.copy(uuid = 1))
                              PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                              PrefManager.setString(PrefManager.ACCESS_TOKEN, it.authToken)

                          }
                          findNavController().navigate(
                             LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                      }
                  }

                }

                else -> {}
            }

        }
    }


}