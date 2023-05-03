package com.example.myapplication.fragment.choosepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentChoosePasswodBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.NetworkResult
import com.example.myapplication.util.PrefManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ChoosePasswodFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChoosePasswodBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChoosePasswordViewModel by viewModels()
    private val args by navArgs<ChoosePasswodFragmentArgs>()
    private var ispasswordValid = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoosePasswodBinding.inflate(inflater, container, false)
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
            if(ispasswordValid) {
                viewModel.verifyPassword(args.RegisterUser)
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.isPasswordValid.collect {
                ispasswordValid = it
                binding.btnContinue.isEnabled = it
               // binding.btnContinue.isClickable = it
            }
        }
        viewModel.verifyPasswordResponse.observe(viewLifecycleOwner)
        { response ->
             when(response){
                 is NetworkResult.Error -> {
                     response.message?.snackBar(requireView(), requireContext())
                 }
                 is NetworkResult.Loading -> {

                 }
                 is NetworkResult.Success -> {
                     Timber.e(response.data.toString())
                     response.data?.data?.let {
                         lifecycleScope.launch{
                             with(Dispatchers.IO){
                                 viewModel.saveUser(it.copy(uuid = 1))
                                 PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                                 PrefManager.setString(PrefManager.ACCESS_TOKEN, it.authToken)
                             }

                         }
                             findNavController().navigate(
                                 ChoosePasswodFragmentDirections.actionChoosePasswodFragmentToHomeFragment()
                             )
                         }

                     }
                 else -> {}
             }

        }
    }


}

