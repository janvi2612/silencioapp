package com.example.myapplication.fragment.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentBottomSheetDialogsBinding
import com.example.myapplication.model.RegisterUserRequestModel
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
 class BottomSheetDialogsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDialogsBinding? = null
    private val binding get() = _binding!!


    private val viewModel: CodeViewModel by viewModels()
    private var isCodevalid = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogsBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        setOnClicks()
        setObserver()

        binding.viewmodels = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }




    private fun setOnClicks() {
        binding.btnclose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btncontinue.setOnClickListener {
            Toast.makeText(requireContext(), "code valid", Toast.LENGTH_LONG).show()
            if (isCodevalid) {
                viewModel.submitCode()
            }

        }
        binding.alreadyHaveAccount.setOnClickListener {
            findNavController().navigate(
             BottomSheetDialogsFragmentDirections.actionBottomSheetDialogsFragmentToLoginFragment()
            )
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.isCodeValid.collect() {
                isCodevalid = it
                binding.btncontinue.isEnabled = it
                binding.btncontinue.isClickable = it

            }
        }
        viewModel.codeResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    Timber.e(response.data.toString())
                    response.data?.data?.id?.let {

                        findNavController().navigate(
                        BottomSheetDialogsFragmentDirections.actionBottomSheetDialogsFragmentToRegisterFragment(
                            RegisterUserRequestModel(referreId = it)
                        )
                        )
                    }
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




