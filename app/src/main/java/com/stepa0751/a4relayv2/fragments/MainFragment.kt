package com.stepa0751.a4relayv2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.databinding.FragmentMainBinding
import com.stepa0751.a4relayv2.models.DataModel
import com.stepa0751.a4relayv2.models.MainViewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mViewModel: MainViewModel

    private var localIsWork: Boolean = false

    //    private var isServiceRunning = false
    private var id1 = true
    private var id2 = true
    private var id3 = true
    private var id4 = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setView()
        setOnClicks()
        onClicks()
        listenMainActivityWeb()
        listenMainActivityLocal()
    }


    @SuppressLint("FragmentLiveDataObserve")
    fun setView() {
        mViewModel.dataLiveData.observe(requireActivity(), Observer {
            id1 = it.one
            id2 = it.two
            id3 = it.free
            id4 = it.four
            binding.tvResponse.text = it.string
            changeButtonState()
        })
    }


    @SuppressLint("FragmentLiveDataObserve")
    fun listenMainActivityWeb() {
        mViewModel.dataTransferWeb.observe(this, Observer {
            val x = it.webPing

            if (it.webPing) {
                binding.tvStatusWeb.text = getString(R.string.web_remote_status_is_ok)
            } else {
                binding.tvStatusWeb.text = getString(R.string.web_remote_status_is_no_connect)
            }
            Log.d("MyLog", "$x")
        })
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun listenMainActivityLocal() {
        mViewModel.dataTransferLocal.observe(this, Observer {
            val x = it.localPing

            if (it.localPing) {
                binding.tvStatusWifi.text = getString(R.string.wifi_remote_status_is_ok)
                localIsWork = true
            } else {
                binding.tvStatusWifi.text = getString(R.string.wifi_remote_status_is_no_connect)
                localIsWork = false
            }
            Log.d("MyLog", "Local:  $x")
        })
    }

    override fun onPause() {
        super.onPause()

        val str = binding.tvResponse.text.toString()
        val items = DataModel(id1, id2, id3, id4, str)
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
                R.id.br1 -> definePressedButtonLocal(1)
                R.id.br2 -> definePressedButtonLocal(2)
                R.id.br3 -> definePressedButtonLocal(3)
                R.id.br4 -> definePressedButtonLocal(4)
                R.id.br8 -> definePressedButtonWeb(1)
                R.id.br9 -> definePressedButtonWeb(2)
                R.id.br10 -> definePressedButtonWeb(3)
                R.id.br11 -> definePressedButtonWeb(4)
            }
        }
    }

    fun definePressedButtonLocal(number: Int) {
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
        changeButtonState()
    }

    fun definePressedButtonWeb(number: Int) {
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
//        if (localIsWork) {
        webSettingDevice(data)
//        }
        changeButtonState()
    }

    @SuppressLint("SetTextI18n")
    fun changeButtonState() {
        if (id1) {
            binding.br1.text =
                "OFF"; binding.br1.setBackgroundColor(resources.getColor(R.color.background_tint))
            binding.br8.text =
                "OFF"; binding.br8.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {
            binding.br1.text =
                "ON"; binding.br1.setBackgroundColor(resources.getColor(R.color.color_backg_button))
            binding.br8.text =
                "ON"; binding.br8.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (id2) {
            binding.br2.text =
                "OFF"; binding.br2.setBackgroundColor(resources.getColor(R.color.background_tint))
            binding.br9.text =
                "OFF"; binding.br9.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {
            binding.br2.text =
                "ON"; binding.br2.setBackgroundColor(resources.getColor(R.color.color_backg_button))
            binding.br9.text =
                "ON"; binding.br9.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (id3) {
            binding.br3.text =
                "OFF"; binding.br3.setBackgroundColor(resources.getColor(R.color.background_tint))
            binding.br10.text =
                "OFF"; binding.br10.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {
            binding.br3.text =
                "ON"; binding.br3.setBackgroundColor(resources.getColor(R.color.color_backg_button))
            binding.br10.text =
                "ON"; binding.br10.setBackgroundColor(resources.getColor(R.color.color_backg_button))
        }
        if (id4) {
            binding.br4.text =
                "OFF"; binding.br4.setBackgroundColor(resources.getColor(R.color.background_tint))
            binding.br11.text =
                "OFF"; binding.br11.setBackgroundColor(resources.getColor(R.color.background_tint))
        } else {
            binding.br4.text =
                "ON"; binding.br4.setBackgroundColor(resources.getColor(R.color.color_backg_button))
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
                Log.d("MyLog", response.toString())
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
