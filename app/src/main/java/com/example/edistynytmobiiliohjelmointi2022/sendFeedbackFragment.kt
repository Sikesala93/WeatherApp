package com.example.edistynytmobiiliohjelmointi2022

import Feedback
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentSendFeedbackBinding
import com.google.gson.GsonBuilder
import java.io.UnsupportedEncodingException


class sendFeedbackFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentSendFeedbackBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendFeedbackBinding.inflate(inflater, container, false)
        val root: View = binding.root


    binding.buttonSendFeedbackApi.setOnClickListener {
        Log.d("ADVTECH", "Button toimii")
        val name = binding.editTextFeedbackName.text.toString()
        val location = binding.editTextFeedbackLocation.text.toString()
        val content = binding.editTextFeedbackContent.text.toString()

        Log.d("ADVTECH",  name + " " + location + " " + content)

        sendFeedbackToApi(name,location,content)
        Log.d("ADVTECH", "sendFeedbackToApi toimii")

    }

        return root
    }

    fun sendFeedbackToApi(name: String, location: String, content: String)
    {
        // this is the url where we want to get our data
        // Note: 10.0.2.2 is for the Android -emulator, it redirects to your computers localhost!
        val JSON_URL = "http://10.0.2.2:8080/apigility/public/feedback"
        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, JSON_URL,
            Response.Listener { response ->
                // response from API, you can use this in TextView, for example
                // Check also out the example below
                // "Handling the JSON in the Volley response" for this part
                // Note: if you send data to API instead, this might not be needed
                Log.d("ADVTECH", response)
                activity?.onBackPressed()
            },

            Response.ErrorListener {
                // typically this is a connection error
                Log.d("ADVTECH", it.toString())
                val toast = Toast.makeText(context, "Error with sending data!", Toast.LENGTH_LONG)
                toast.show()
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // we have to specify a proper header, otherwise Apigility will block our queries!
                // define we are after JSON data!
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                // replace with your own API's login info


                /* val authorizationString = "Basic " + Base64.encodeToString(
                    (BuildConfig.APIGILITY_USER + ":" + BuildConfig.APIGILITY_PASSWORD).toByteArray(), Base64.DEFAULT
                )
                headers.put("Authorization", authorizationString);*/
                return headers
            }

            // build the data from edittexts
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                // this function is only needed when sending data
                var body = ByteArray(0)
                try { // check the example "Converting a Kotlin object to JSON"
                    // on how to create this newData -variable
                    var newData = ""
                    // build new data in Kotlin
                   /* var f : Feedback = Feedback()
                    f.name = name
                    f.location = location
                    f.value = content*/

                    val f : Feedback = Feedback(name = name, location = location, value = content)

                    // use Gson to convert Kotlin feedback to JSON
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    newData = gson.toJson(f)
                    body = newData.toByteArray(Charsets.UTF_8)
                } catch (e: UnsupportedEncodingException) {
                    // problems with converting our data into UTF-8 bytes
                }
                return body
            }
        }
        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
