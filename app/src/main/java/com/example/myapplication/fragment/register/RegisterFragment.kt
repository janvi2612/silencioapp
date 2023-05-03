package com.example.myapplication.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BottomSheetDialogFragment() {
    private  var _binding: FragmentRegisterBinding?=null
    private val binding get() = _binding!!




    private val args by navArgs<RegisterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding=FragmentRegisterBinding.inflate(inflater,container,false)
        (dialog as? BottomSheetDialog)?.behavior?.state= BottomSheetBehavior.STATE_EXPANDED
        setOnClick()
        return binding.root
    }

    private fun setOnClick(){
        binding.btnphnono.setOnClickListener {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToRegisterphnoFragment(args.registerUser)
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}