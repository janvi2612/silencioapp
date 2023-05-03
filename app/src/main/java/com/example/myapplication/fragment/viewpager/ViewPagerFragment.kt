package com.example.myapplication.fragment.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentViewPagerBinding
import com.example.myapplication.util.Constant1
import com.example.myapplication.util.PrefManager
import com.google.android.material.tabs.TabLayoutMediator



class ViewPagerFragment : Fragment() {
    private var _binding:FragmentViewPagerBinding?=null
    private val binding get()=_binding!!

    private lateinit var adapter: ViewPagerAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentViewPagerBinding.inflate(inflater,container,false)
        setUpUi()
        setOnClicks()
        return binding.root
    }
    private fun setUpUi(){
      adapter=ViewPagerAdapter(requireActivity())
        binding.viewTransition.adapter=adapter
        TabLayoutMediator(binding.TircleIndicator3,binding.viewTransition){
            _,_,->
        }.attach()

        binding.viewTransition.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnOnBoardingContinue.isEnabled = position == 3
            }
        })
    }

    private fun setOnClicks(){
        binding.btnOnBoardingContinue.setOnClickListener{
      PrefManager.setBoolean(Constant1.RESPONSE_CODE.IS_INTRO_COMPLETE,true)
            findNavController().navigate(
               ViewPagerFragmentDirections.actionViewPagerFragmentToBottomSheetDialogsFragment()

           )

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    }
