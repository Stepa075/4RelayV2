package com.stepa0751.a4relayv2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stepa0751.a4relayv2.R
import com.stepa0751.a4relayv2.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    //    private var isServiceRunning = false
    private var id1 = false
    private var id2 = false
    private var id3 = false
    private var id4 = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLog", "onViewCreated")
//        Этой строкой обновляем данные во вьюМодел
 //       homeViewModel.message.value = binding.br1.text.toString()
//        Этой строкой принимаем данные из вьюМодел
//        homeViewModel.message.observe(viewLifecycleOwner) {
//            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//            binding.br1.text = it.toString()
//        }
        setOnClicks()
        onClicks()
    }

    //  Функция инициализации слушателя нажатий для ВСЕГО ВЬЮ
    fun setOnClicks() = with(binding) {
        val listener = onClicks()
        br1.setOnClickListener(listener)
        br2.setOnClickListener(listener)
        br3.setOnClickListener(listener)
        br4.setOnClickListener(listener)
    }

    //  Функция сработки слушателя нажатий на этом вью
    private fun onClicks(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.br2 -> startStopService(4)
                R.id.br1 -> startStopService(5)
                R.id.br3 -> startStopService(12)
                R.id.br4 -> startStopService(14)

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startStopService(id: Int) {

        val queue = Volley.newRequestQueue(context)

        if (when (id) {
                4 -> !id1
                5 -> !id2
                12 -> !id3
                14 -> !id4
                else -> {
                    false
                }
            }
        ) {
            val url =
                "http://192.168.1.16/gpio$id/0"
            val sRequest = StringRequest(
                Request.Method.GET,
                url, { response ->
                    Log.d("MyLog", "All OK! GPIO$id request: $response")
                },
                { Log.d("MyLog", "Error request: $it") }
            )
            queue.add(sRequest)
            when (id) {
                4 -> id1 = !id1
                5 -> id2 = !id2
                12 -> id3 = !id3
                14 -> id4 = !id4
                else -> Log.d("MyLog", "Error request: $id")
            }
            when (id) {
                4 -> {
                    binding.br2.text =
                        "ON"; binding.br2.setBackgroundColor(resources.getColor(R.color.color_backg_button))
                }

                5 -> {
                    binding.br1.text =
                        "ON";   binding.br1.setBackgroundColor(resources.getColor(R.color.color_backg_button))
                }

                12 -> {
                    binding.br3.text =
                        "ON"; binding.br3.setBackgroundColor(resources.getColor(R.color.color_backg_button))
                }

                14 -> {
                    binding.br4.text =
                        "ON"; binding.br4.setBackgroundColor(resources.getColor(R.color.color_backg_button))
                }

                else -> Log.d("MyLog", "Error request: $id")
            }

        } else {
            val url =
                "http://192.168.1.16/gpio$id/1"
            val sRequest = StringRequest(
                Request.Method.GET,
                url, { response ->
                    Log.d("MyLog", "All OK! request: $response")
                },
                { Log.d("MyLog", "Error request: $it") }
            )
            queue.add(sRequest)
            when (id) {
                4 -> id1 = !id1
                5 -> id2 = !id2
                12 -> id3 = !id3
                14 -> id4 = !id4
                else -> Log.d("MyLog", "Error request: $id")
            }
            when (id) {
                4 -> {
                    binding.br2.text =
                        "OFF"; binding.br2.setBackgroundColor(resources.getColor(R.color.background_tint))
                }

                5 -> {
                    binding.br1.text =
                        "OFF";  binding.br1.setBackgroundColor(resources.getColor(R.color.background_tint))
                }

                12 -> {
                    binding.br3.text =
                        "OFF"; binding.br3.setBackgroundColor(resources.getColor(R.color.background_tint))
                }

                14 -> {
                    binding.br4.text =
                        "OFF"; binding.br4.setBackgroundColor(resources.getColor(R.color.background_tint))
                }

                else -> Log.d("MyLog", "Error request: $id")
            }
        }

//        isServiceRunning = !isServiceRunning
    }









    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}