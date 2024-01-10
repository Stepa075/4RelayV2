package com.stepa0751.a4relayv2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.databinding.FragmentMainBinding
import com.stepa0751.a4relayv2.databinding.FragmentStartBinding
import com.stepa0751.a4relayv2.utils.openFragment


class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
            binding.but2.setOnClickListener{
                openFragment(MainFragment.newInstance())
            }
        return binding.root  //inflater.inflate(R.layout.fragment_start, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}