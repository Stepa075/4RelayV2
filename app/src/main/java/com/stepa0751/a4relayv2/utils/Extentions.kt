package com.stepa0751.a4relayv2.utils

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stepa0751.a4relayv2.R

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

fun Fragment.showLog(s:String, v:String){
    Log.d(s, v)
}

fun AppCompatActivity.showLog(s:String, v:String){
    Log.d(s, v)
}

fun Fragment.myLog(v:String){
    Log.d("MyLog", v)
}

fun AppCompatActivity.myLog(v:String){
    Log.d("MyLog", v)
}