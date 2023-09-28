package com.stepa0751.a4relayv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stepa0751.a4relayv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}