package com.stepa0751.a4relayv2.utils

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.models.MainViewModel
import com.stepa0751.a4relayv2.models.TransferDataCurrent
import com.stepa0751.a4relayv2.models.TransferDataLocal
import org.json.JSONObject

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





