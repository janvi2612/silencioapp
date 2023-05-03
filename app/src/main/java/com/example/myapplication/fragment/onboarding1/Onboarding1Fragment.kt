package com.example.myapplication.fragment.onboarding1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentOnboarding1Binding


class Onboarding1Fragment( private val text1: String,private val text2: String, private val img: Int): Fragment() {
  private var _binding:FragmentOnboarding1Binding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding1Binding.inflate(LayoutInflater.from(context))
        setupi()
        return binding.root
    }

    private fun setupi() {
        binding.txt1.text=text1
        binding.txt2.text=text2
        binding.imgview.setImageResource(img)
    }


}