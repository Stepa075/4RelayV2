package com.stepa0751.a4relayv2.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.stepa0751.a4relayv2.R


// Блядь!!! Преференсы должны быть версии 1.1.1!!!!! СУКА!!!

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preference, rootKey)
    }


}





