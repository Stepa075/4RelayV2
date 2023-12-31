package com.stepa0751.a4relayv2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stepa0751.a4relayv2.databinding.ActivityMainBinding
import com.stepa0751.a4relayv2.fragments.MainFragment
import com.stepa0751.a4relayv2.fragments.MeteoFragment
import com.stepa0751.a4relayv2.fragments.SettingsFragment
import com.stepa0751.a4relayv2.fragments.StartFragment
import com.stepa0751.a4relayv2.fragments.TechFragment
import com.stepa0751.a4relayv2.models.LocalReceivedData
import com.stepa0751.a4relayv2.models.MainViewModel
import com.stepa0751.a4relayv2.models.TransferData
import com.stepa0751.a4relayv2.models.TransferDataLocal
import com.stepa0751.a4relayv2.models.TransferReceivedData
import com.stepa0751.a4relayv2.utils.openFragment
import org.json.JSONObject
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private val timerWeb = Timer()
    private val timerLoc = Timer()
    private val timerPool = Timer()
    private val localPool = Timer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        onBottomNavClicks()
        myTimerWeb(timerWeb)
        myTimerLoc(timerLoc)
        myTimerChannelPool(timerPool)
        myTimerLocalPool(localPool)
        openFragment(StartFragment.newInstance())
    }


    private fun onBottomNavClicks() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.id_home-> openFragment(StartFragment.newInstance())
                R.id.id_relays -> openFragment(MainFragment.newInstance())
                R.id.id_settings -> openFragment(SettingsFragment())
                R.id.id_meteo-> openFragment(MeteoFragment.newInstance())
                R.id.id_tech-> openFragment(TechFragment.newInstance())
            }
            true
        }
    }


    private fun myTimerLoc(timerLoc: Timer) {
        val period = PreferenceManager.getDefaultSharedPreferences(this).
        getString("key_upd_time_local", "30000")
        if (period != null) {
            timerLoc.schedule(delay = 0, period = period.toLong()) {
                testingLocal()
            }
        }
    }

    private fun myTimerWeb(timerWeb: Timer) {
        val period = PreferenceManager.getDefaultSharedPreferences(this).
        getString("key_upd_time_ping_web_host", "30000")
        if (period != null) {
            timerWeb.schedule(delay = 0, period = period.toLong()) {
                testingWeb()

            }
        }
    }

    private fun myTimerChannelPool(timerPool: Timer) {
        val period = PreferenceManager.getDefaultSharedPreferences(this).
        getString("key_upd_time_rec_channel_poll", "30000")
        if (period != null) {
            timerPool.schedule(delay = 0, period = period.toLong()) {
                botGetData()

            }
        }
    }
    private fun myTimerLocalPool(timerPool: Timer) {
        val period = PreferenceManager.getDefaultSharedPreferences(this).
        getString("key_update_local_data", "5000")
        if (period != null) {
            timerPool.schedule(delay = 0, period = period.toLong()) {
                localGetData()

            }
        }
    }

    private fun testingWeb() {
        val host = PreferenceManager.getDefaultSharedPreferences(this).getString("key_web_host", "8.8.8.8")
        val queue = Volley.newRequestQueue(this)
        val url = "https://$host/"
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val ok = true
                val items = TransferData(ok)
                mViewModel.dataTransferWeb.value = items
            },
            {
                val nook = false
                val items = TransferData(nook)
                mViewModel.dataTransferWeb.value = items
            },
        )
        queue.add(sRequest)
    }

    private fun testingLocal() {
        val host = PreferenceManager.getDefaultSharedPreferences(this).getString("key_local_url_sh", "192.168.1.200")
        val port = PreferenceManager.getDefaultSharedPreferences(this).getString("key_local_port", "2000")
        val queue = Volley.newRequestQueue(this)
        val url = "http://${host}:${port}/alex"
//        Log.d("MyLog", "url = $url")
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val ok = true
                val items = TransferDataLocal(ok)
                mViewModel.dataTransferLocal.value = items
                Log.d("MyLog", response.toString())
            },
            {
                val nook = false
                val items = TransferDataLocal(nook)
                mViewModel.dataTransferLocal.value = items
            },
        )
        queue.add(sRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerWeb.cancel()
        timerLoc.cancel()
        timerPool.cancel()
        localPool.cancel()
    }


    private fun botGetData() {
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString("key_token", "")
        val channel_id = PreferenceManager.getDefaultSharedPreferences(this).getString("key_receiving", "")
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.telegram.org/bot${token}/getUpdates?chat_id=${channel_id}&offset=-1"
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val x = response
//                Log.d("MyLog", "response: ${x}")
               val items = TransferReceivedData(x)
                mViewModel.dataReceived.value = items

            },
            {
                val nook = false
                val items = TransferData(nook)
                mViewModel.dataTransferWeb.value = items
            },
        )
        queue.add(sRequest)
    }


    private fun localGetData() {
        val host = PreferenceManager.getDefaultSharedPreferences(this).getString("key_local_url_sh", "192.168.1.200")
        val port = PreferenceManager.getDefaultSharedPreferences(this).getString("key_local_port", "2000")
        val queue = Volley.newRequestQueue(this)
        val url = "http://${host}:${port}/data_response_to_client"
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val x = response
//                Log.d("MyLog", "response: ${x}")
                val items = LocalReceivedData(x)
                mViewModel.localDataReceived.value = items
            },
            {

                val items = LocalReceivedData("error")
                mViewModel.localDataReceived.value = items
            },
        )
        queue.add(sRequest)
    }





}