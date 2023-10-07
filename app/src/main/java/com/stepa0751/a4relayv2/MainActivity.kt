package com.stepa0751.a4relayv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stepa0751.a4relayv2.databinding.ActivityMainBinding
import com.stepa0751.a4relayv2.fragments.HomeMaxFragment
import com.stepa0751.a4relayv2.fragments.MainFragment
import com.stepa0751.a4relayv2.fragments.SettingsFragment
import com.stepa0751.a4relayv2.utils.openFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBottomNavClicks()
        openFragment(MainFragment.newInstance())
    }


    private fun onBottomNavClicks(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.id_home -> openFragment(MainFragment.newInstance())
                R.id.id_homemax -> openFragment(HomeMaxFragment.newInstance())
                R.id.id_settings ->openFragment(SettingsFragment())
            }
            true
        }
    }
}