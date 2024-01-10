package com.stepa0751.a4relayv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stepa0751.a4relayv2.MainActivity
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.databinding.FragmentMainBinding
import com.stepa0751.a4relayv2.models.DataModel
import com.stepa0751.a4relayv2.models.MainViewModel
import com.stepa0751.a4relayv2.utils.showToast
import com.stepa0751.a4relayv2.utils.vibratePhone
import org.json.JSONObject


@SuppressLint("UseSwitchCompatOrMaterialCode")
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var switch:Switch
    private var switchFlag:Boolean = false
    private var localIsWork: Boolean = false

    //    private var isServiceRunning = false
    private var id1 = true
    private var id2 = true
    private var id3 = true
    private var id4 = true
    private var id5 = true
    private var id7 = true
    private var id6 = true
    private var id8 = true




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val switch = binding.switch2
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                switchFlag = true
                lockUnlockButtons("switch", false)
                binding.switch2.text = getString(R.string.web_mode_off)
            }else{
                switchFlag = false
                lockUnlockButtons("web", true)
                binding.switch2.text = getString(R.string.web_mode_on)
            }
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        requireActivity()
        mViewModel = ViewModelProvider(activity as AppCompatActivity)[MainViewModel::class.java]
        setView()
        setOnClicks()
        onClicks()
        listenMainActivityWeb()
        listenMainActivityLocal()
        listenChannelUpdate()
        listenLocalUpdate()

    }



    @SuppressLint("FragmentLiveDataObserve")
    fun setView() {
        mViewModel.dataLiveData.observe(this, Observer {
            id1 = it.one
            id2 = it.two
            id3 = it.free
            id4 = it.four
            id5 = it.five
            id6 = it.six
            id7 = it.seven
            id8 = it.eight
            binding.tvResponse.text = it.string
//            changeButtonState()

        })
    }


    private fun lockUnlockButtons(idRemote: String, state: Boolean) {
        if (idRemote == "loc") {
            binding.br1.isEnabled = state
            binding.br2.isEnabled = state
            binding.br3.isEnabled = state
            binding.br4.isEnabled = state
        } else if (switchFlag) {
            binding.br8.isEnabled = false
            binding.br9.isEnabled = false
            binding.br10.isEnabled = false
            binding.br11.isEnabled = false
        } else if (idRemote == "web") {
            binding.br8.isEnabled = state
            binding.br9.isEnabled = state
            binding.br10.isEnabled = state
            binding.br11.isEnabled = state
        }
    }


    @SuppressLint("FragmentLiveDataObserve")
    fun listenMainActivityWeb() {
        mViewModel.dataTransferWeb.observe(this, Observer {
            val x = it.webPing
            if (it.webPing) {
                binding.tvStatusWeb.text = getString(R.string.web_remote_status_is_ok)
                lockUnlockButtons("web", true)
            } else {
                binding.tvStatusWeb.text = getString(R.string.web_remote_status_is_no_connect)
                lockUnlockButtons("web", false)
            }
        })
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun listenMainActivityLocal() {
        mViewModel.dataTransferLocal.observe(this, Observer {
            val x = it.localPing

            if (it.localPing) {
                binding.tvStatusWifi.text = getString(R.string.wifi_remote_status_is_ok)
                localIsWork = true
                lockUnlockButtons("loc", true)
            } else {
                binding.tvStatusWifi.text = getString(R.string.wifi_remote_status_is_no_connect)
                localIsWork = false
                lockUnlockButtons("loc", false)

            }
//            Log.d("MyLog", "Local:  $x")
        })
    }
//    Посмотреть как можно улучшить проверку на пустой массив JSONArray in 170 line
    @SuppressLint("FragmentLiveDataObserve", "SetTextI18n")
    fun listenChannelUpdate() {
        mViewModel.dataReceived.observe(this, Observer {
            if (it.data != "error") {
                try {
                    val mainObject = JSONObject(it.data)
                    val jarray = mainObject.getJSONArray("result")
                    val mainArray = jarray[0] as JSONObject
                    val lastObject = mainArray.getJSONObject("channel_post").getString("text")
                    val strObj = JSONObject(lastObject)
                    val data = strObj.getString("201")
                    val value1 = data.substringBefore(':').toInt()
                    val value2 =
                        data.substringAfter(':').substringBefore(':').substringBefore(':').toInt()
                    val value3 = data.substringAfter(':').substringAfter(':').substringBefore('.')
                        .substringBeforeLast(':').toInt()
                    val value4 = data.substringAfterLast(':').toInt()
                    Log.d("MyLog", "Data from web channel : $data")
                    Log.d("MyLog", "Data from web channel 1: $value1")
                    Log.d("MyLog", "Data from web channel 2: $value2")
                    Log.d("MyLog", "Data from web channel 3: $value3")
                    Log.d("MyLog", "Data from web channel 3: $value4")
                    changeWebButtonState(value1, value2, value3, value4)
                    binding.tvResponse3.text = ""
                } catch (e: Exception) {
                    val value1 = 1
                    val value2 = 1
                    val value3 = 1
                    val value4 = 1
                    changeWebButtonState(value1, value2, value3, value4)
                    binding.tvResponse3.text = getString(R.string.error_accept_information)

                }
            } else {
                val value1 = 1
                val value2 = 1
                val value3 = 1
                val value4 = 1
                changeWebButtonState(value1, value2, value3, value4)
                binding.tvResponse3.text = getString(R.string.error_accept_information)

            }

        })
    }

    @SuppressLint("FragmentLiveDataObserve", "SetTextI18n")
    fun listenLocalUpdate() {
        mViewModel.localDataReceived.observe(this, Observer {
            if (it.data != "error") {
                val mainObject = JSONObject(it.data)
//                val jarray = mainObject.getJSONArray("result")
//                val mainArray = jarray[0] as JSONObject
//                val lastObject = mainArray.getJSONObject("channel_post").getString("text")
//                val strObj = JSONObject(lastObject)
                val data = mainObject.getString("201")
                val value1 = data.substringBefore(':').toInt()
                val value2 =
                    data.substringAfter(':').substringBefore(':').substringBefore(':').toInt()
                val value3 = data.substringAfter(':').substringAfter(':').substringBefore('.')
                    .substringBeforeLast(':').toInt()
                val value4 = data.substringAfterLast(':').toInt()
                Log.d("MyLog", "Data from local channel : $data")
                Log.d("MyLog", "Data from local channel 1: $value1")
                Log.d("MyLog", "Data from local channel 2: $value2")
                Log.d("MyLog", "Data from local channel 3: $value3")
                Log.d("MyLog", "Data from local channel 3: $value4")
                changeButtonState(value1, value2, value3, value4)
                binding.tvResponse.text = ""
            } else {
                val value1 = 1
                val value2 = 1
                val value3 = 1
                val value4 = 1
                changeButtonState(value1, value2, value3, value4)
                binding.tvResponse.text = "ERROR!!!"
            }

        })
    }


    override fun onPause() {
        super.onPause()

        val str = binding.tvResponse.text.toString()
        val items = DataModel(id1, id2, id3, id4, id5, id6, id7, id8, str)
        mViewModel.dataLiveData.value = items

    }

    //  Функция инициализации слушателя нажатий для ВСЕГО ВЬЮ
    fun setOnClicks() = with(binding) {
        val listener = onClicks()
        br1.setOnClickListener(listener)
        br2.setOnClickListener(listener)
        br3.setOnClickListener(listener)
        br4.setOnClickListener(listener)
        br8.setOnClickListener(listener)
        br9.setOnClickListener(listener)
        br10.setOnClickListener(listener)
        br11.setOnClickListener(listener)
    }

    //  Функция сработки слушателя нажатий на этом вью
    private fun onClicks(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.br1 -> {
                    definePressedButtonLocal(1)
                    vibratePhone()
                }

                R.id.br2 -> {
                    definePressedButtonLocal(2)
                    vibratePhone()
                }

                R.id.br3 -> {
                    definePressedButtonLocal(3)
                    vibratePhone()
                }

                R.id.br4 -> {
                    definePressedButtonLocal(4)
                    vibratePhone()
                }

                R.id.br8 -> {
                    definePressedButtonWeb(5)
                    vibratePhone()
                }

                R.id.br9 -> {
                    definePressedButtonWeb(6)
                    vibratePhone()
                }

                R.id.br10 -> {
                    definePressedButtonWeb(7)
                    vibratePhone()
                }

                R.id.br11 -> {
                    definePressedButtonWeb(8)
                    vibratePhone()
                }
            }
        }
    }

    private fun definePressedButtonLocal(number: Int) {
        if (number == 1) {
            id1 = !id1
        } else if (number == 2) {
            id2 = !id2
        } else if (number == 3) {
            id3 = !id3
        } else if (number == 4) {
            id4 = !id4
        }
        val data =
            "${if (id1) 1 else 0}:${if (id2) 1 else 0}:${if (id3) 1 else 0}:${if (id4) 1 else 0}"
        if (localIsWork) {
            settingDevice(data)
        }
//        changeButtonState()
    }

    private fun definePressedButtonWeb(number: Int) {
        if (number == 5) {
            id5 = !id5
        } else if (number == 6) {
            id6 = !id6
        } else if (number == 7) {
            id7 = !id7
        } else if (number == 8) {
            id8 = !id8
        }
        val data =
            "201_${if (id5) 1 else 0}:${if (id6) 1 else 0}:${if (id7) 1 else 0}:${if (id8) 1 else 0}"
//        if (localIsWork) {
        webSettingDevice(data)
//        }
//        changeButtonState()
    }

    @SuppressLint("SetTextI18n")
    fun changeButtonState(val1:Int, val2:Int, val3:Int, val4:Int) {
        if (val1 == 1) {
            binding.br1.text =
                "OFF"; binding.br1.setBackgroundColor(resources.getColor(R.color.background_tint))

        } else {
            binding.br1.text =
                "ON"; binding.br1.setBackgroundColor(resources.getColor(R.color.color_backg_button))

        }
        if (val2 == 1) {
            binding.br2.text =
                "OFF"; binding.br2.setBackgroundColor(resources.getColor(R.color.background_tint))

        } else {
            binding.br2.text =
                "ON"; binding.br2.setBackgroundColor(resources.getColor(R.color.color_backg_button))

        }
        if (val3 == 1) {
            binding.br3.text =
                "OFF"; binding.br3.setBackgroundColor(resources.getColor(R.color.background_tint))

        } else {
            binding.br3.text =
                "ON"; binding.br3.setBackgroundColor(resources.getColor(R.color.color_backg_button))

        }
        if (val4 == 1) {
            binding.br4.text =
                "OFF"; binding.br4.setBackgroundColor(resources.getColor(R.color.background_tint))

        } else {
            binding.br4.text =
                "ON"; binding.br4.setBackgroundColor(resources.getColor(R.color.color_backg_button))

        }

    }

    @SuppressLint("SetTextI18n")
    fun changeWebButtonState(val1:Int, val2:Int, val3:Int, val4:Int) {
        if (val1 == 1) {

            binding.br8.text =
                "OFF"; binding.br8.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {

            binding.br8.text =
                "ON"; binding.br8.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (val2 == 1) {

            binding.br9.text =
                "OFF"; binding.br9.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {

            binding.br9.text =
                "ON"; binding.br9.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (val3 == 1) {

            binding.br10.text =
                "OFF"; binding.br10.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {

            binding.br10.text =
                "ON"; binding.br10.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (val4 == 1) {

            binding.br11.text =
                "OFF"; binding.br11.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {

            binding.br11.text =
                "ON"; binding.br11.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }

    }


    private fun settingDevice(data: String) {
        val host = PreferenceManager.getDefaultSharedPreferences(activity as AppCompatActivity)
            .getString("key_local_url_sh", "192.168.1.200")
        val port = PreferenceManager.getDefaultSharedPreferences(activity as AppCompatActivity)
            .getString("key_local_port", "2000")
        val queue = Volley.newRequestQueue(activity as AppCompatActivity)
        val url = "http://${host}:${port}/data_set_to_device?id=201&data=$data"
        Log.d("MyLog", "url = $url")
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
//                Log.d("MyLog", response.toString())
            },
            {
                Log.d("MyLog", "Error setting device")
            },
        )
        queue.add(sRequest)
    }


    private fun webSettingDevice(data: String) {
        val token = PreferenceManager.getDefaultSharedPreferences(activity as AppCompatActivity)
            .getString("key_token", "")
        val transChatId = PreferenceManager.getDefaultSharedPreferences(activity as AppCompatActivity)
            .getString("key_transmitting", "")
        val queue = Volley.newRequestQueue(activity as AppCompatActivity)
        val url = "https://api.telegram.org/bot$token/sendmessage?chat_id=$transChatId&text=$data"
        Log.d("MyLog", "url = $url")
        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("MyLog", response.toString())
            },
            {
                Log.d("MyLog", "Error setting device")
            },
        )
        queue.add(sRequest)
    }



    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}


