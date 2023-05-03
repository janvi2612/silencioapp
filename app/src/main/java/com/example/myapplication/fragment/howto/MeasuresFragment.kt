package com.example.myapplication.fragment.howto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMeasuresBinding
import com.example.myapplication.fragment.bottomsheet.BottomSheetDialogsFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MeasuresFragment : BottomSheetDialogFragment(){
    private var _binding: FragmentMeasuresBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMeasuresBinding.inflate(inflater,container,false)
        return binding.root
    }


}