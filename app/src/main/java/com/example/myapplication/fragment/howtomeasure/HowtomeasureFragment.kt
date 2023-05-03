package com.example.myapplication.fragment.howtomeasure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHowtoBinding
import com.example.myapplication.databinding.FragmentHowtomeasureBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HowtomeasureFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentHowtomeasureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentHowtomeasureBinding.inflate(inflater,container,false)
        return binding.root
    }


}