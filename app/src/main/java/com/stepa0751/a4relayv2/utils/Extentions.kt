package com.stepa0751.a4relayv2.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect.*
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.databinding.FragmentStartBinding
import com.stepa0751.a4relayv2.fragments.MainFragment
import com.stepa0751.a4relayv2.fragments.MeteoFragment
import com.stepa0751.a4relayv2.fragments.SettingsFragment
import com.stepa0751.a4relayv2.fragments.StartFragment
import com.stepa0751.a4relayv2.fragments.TechFragment

fun Fragment.openFragment(f: Fragment) {
    (activity as AppCompatActivity).supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        ).replace(R.id.placeHolder, f).commit()
}

fun AppCompatActivity.openFragment(f: Fragment) {
        if (supportFragmentManager.fragments.isNotEmpty()){
            if(supportFragmentManager.fragments[0].javaClass == f.javaClass) return
        }
        supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        ).replace(R.id.placeHolder, f).commit()
    
    }

fun Fragment.showToast(s: String) {
    Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun Fragment.myLog(v:String){
    Log.d("MyLog", v)
}

fun AppCompatActivity.myMyLog(v:String){
    Log.d("MyLog", v)
}

@Suppress("DEPRECATION")
fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(createOneShot(200, DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}





 



