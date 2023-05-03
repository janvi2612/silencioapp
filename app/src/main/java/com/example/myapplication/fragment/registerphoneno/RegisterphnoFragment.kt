package com.example.myapplication.fragment.registerphoneno

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterphnoBinding
import com.example.myapplication.fragment.bottomsheet.BottomSheetDialogsFragmentDirections
import com.example.myapplication.model.RegisterUserRequestModel
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RegisterphnoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRegisterphnoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterphnViewModel by viewModels()
    private val args by navArgs<RegisterphnoFragmentArgs>()

    private var isPhoneValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterphnoBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        binding.viewModels = viewModel
        binding.lifecycleOwner = this
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
                viewModel.verifyNumber(binding.edtcountrycodepicker.selectedCountryCodeWithPlus)
            }
        }

    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.isPhoneNumberValid.collect {
                isPhoneValid = it
                binding.btncontinue.isEnabled = it
                binding.btncontinue.isClickable = it
            }
        }

        viewModel.verifyNumberResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    findNavController().navigate(
                         RegisterphnoFragmentDirections.actionRegisterphnoFragmentToCreateaccountFragment(
                             args.RegisterUser.copy(
                                 countryCode = binding.edtcountrycodepicker.selectedCountryCodeWithPlus,
                                 phone = viewModel.phoneNumber.value

                             )
                         )
                     )
                }

                else -> {}
            }
        }


    }
}