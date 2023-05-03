package com.example.myapplication.fragment.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.databinding.FragmentMeasuresBinding
import com.example.myapplication.util.Constant1


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        setOnClicks()
        return binding.root
    }

    private fun setOnClicks() {
        binding.webview.loadUrl("https://www.zignuts.com/")


    }
}