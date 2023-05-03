package com.example.myapplication.fragment.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentCreateaccountBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateaccountFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateaccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateaccountViewModel by viewModels()
    private val args by navArgs<CreateaccountFragmentArgs>()

    private var isNickNameValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateaccountBinding.inflate(inflater, container, false)
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
        binding.btnContinue.setOnClickListener {
            Toast.makeText(requireContext(), "code valid", Toast.LENGTH_LONG).show()
            if (isNickNameValid) {
                viewModel.verifyNickName()
            }

        }
    }

    private fun setObservers() {
      lifecycleScope.launch {
          viewModel.isNickNameValid.collect{
              isNickNameValid = it
              binding.btnContinue.isEnabled = it
              binding.btnContinue.isClickable = it
          }
      }
        viewModel.verifyNickNameResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Success -> {

                    findNavController().navigate(
                        CreateaccountFragmentDirections.actionCreateaccountFragmentToChoosePasswodFragment(
                            args.registerUser.copy(
                                firstName = viewModel.firstName.value,
                                lastName = viewModel.lastName.value,
                                nickName = viewModel.nickName.value

                            )
                        )
                    )
                }

                else -> {}
            }

        }
    }

}