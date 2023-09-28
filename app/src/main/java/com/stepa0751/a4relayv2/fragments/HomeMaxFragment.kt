package com.stepa0751.a4relayv2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stepa0751.a4relayv2.databinding.FragmentHomeMaxBinding


class HomeMaxFragment : Fragment() {
    private lateinit var binding: FragmentHomeMaxBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMaxBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeMaxFragment()
    }
}