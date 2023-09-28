package com.stepa0751.a4relayv2.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import com.stepa0751.a4relayv2.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var updateTimePref: Preference
    private lateinit var updateColorPref: Preference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferense, rootKey)
        init()
    }

    private fun init() {
        updateTimePref = findPreference("update_time_key")!!
        updateColorPref = findPreference("update_color_key")!!
        val changeListener = onChangeListener()
        updateTimePref.onPreferenceChangeListener = changeListener
        updateColorPref.onPreferenceChangeListener = changeListener
        initPrefs()
    }

    private fun onChangeListener(): OnPreferenceChangeListener {
        return Preference.OnPreferenceChangeListener { pref, value ->
            when (pref.key) {
                "update_time_key" -> onTimeChange(value.toString())
                "update_color_key" -> pref.icon?.setTint((Color.parseColor(value.toString())))
            }

            true
        }
    }

    private fun onTimeChange(value: String) {
        val nameArray = resources.getStringArray(R.array.loc_time_update_name)
        val valueArray = resources.getStringArray(R.array.loc_time_update_value)
        val title = updateTimePref.title.toString().substringBefore(":")
        updateTimePref.title = "$title: ${nameArray[valueArray.indexOf(value)]}"
    }

    private fun initPrefs() {
        val pref = updateTimePref.preferenceManager.sharedPreferences
        val nameArray = resources.getStringArray(R.array.loc_time_update_name)
        val valueArray = resources.getStringArray(R.array.loc_time_update_value)
        val title = updateTimePref.title
        updateTimePref.title =
            "$title: ${nameArray[valueArray.indexOf(pref?.getString("update_time_key", "5000"))]}"

        val color = pref?.getString("update_color_key", "#FFCD0C32")
        updateColorPref.icon?.setTint(Color.parseColor(color))
    }

}