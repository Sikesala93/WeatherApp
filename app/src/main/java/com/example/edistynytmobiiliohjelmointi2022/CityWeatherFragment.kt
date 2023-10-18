package com.example.edistynytmobiiliohjelmointi2022

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentCityWeatherBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataDetailBinding
import com.google.gson.GsonBuilder


class CityWeatherFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentCityWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: CityWeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val test : String = BuildConfig.OPENWEATHER_API_KEY

        // the binding -object allows you to access views in the layout, textviews etc.
        Log.d("ADVTECH", "Argumentti: " + args.cityName)

        getWeatherData()

        return root
    }

    fun getWeatherData()
    {
        // this has been saved in local.properties file (gradle-files)
        val API_KEY : String = BuildConfig.OPENWEATHER_API_KEY
        // this is the url where we want to get our data from
        val JSON_URL = "https://api.openweathermap.org/data/2.5/weather?q=${args.cityName}&appid=$API_KEY&units=metric"
        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->
                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                Log.d("ADVTECH", response)

                val gson = GsonBuilder().setPrettyPrinting().create()
                var item : CityWeather = gson.fromJson(response, CityWeather::class.java)

                Log.d("ADVTECH", item.main?.temp.toString())


            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("ADVTECH", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }
        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}