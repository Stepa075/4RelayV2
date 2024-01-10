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
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
            binding.cv2.setOnClickListener{
                openFragment(MainFragment.newInstance())
            }
        binding.cv3.setOnClickListener{
            openFragment(MeteoFragment.newInstance())
        }
        binding.cv4.setOnClickListener{
            openFragment(TechFragment.newInstance())
        }
        binding.cv5.setOnClickListener{
            openFragment(SettingsFragment())
        }
        return binding.root  //inflater.inflate(R.layout.fragment_start, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}