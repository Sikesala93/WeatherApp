package com.example.edistynytmobiiliohjelmointi2022

import Feedback
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataReadBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentFeedbackReadBinding
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_feedback_read.*
import org.json.JSONObject
import java.io.UnsupportedEncodingException

var feedbacks : List<Feedback> = emptyList();

class FeedbackReadFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentFeedbackReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

       getFeedbacks()


        binding.buttonSendFeedback.setOnClickListener {
        val action = FeedbackReadFragmentDirections.actionFeedbackReadFragmentToSendFeedbackFragment()
            it.findNavController().navigate(action)
        }

        binding.ListviewFeedbacks

        return root
    }


    fun getFeedbacks() {
        // this is the url where we want to get our data
        // Note: 10.0.2.2 is for the Android -emulator, it redirects to your computers localhost!
        val JSON_URL = "http://10.0.2.2:8080/apigility/public/feedback"
        // 192.168.1.104

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->
                Log.d("ADVTECH", "raakaJson")
                Log.d("ADVTECH", response)

                val gson = GsonBuilder().setPrettyPrinting().create()

                val jObject = JSONObject(response)
                val embedObject = jObject.getJSONObject("_embedded")
                Log.d("ADVTECH", "embedded toimii")
                // in this case feedback is the name of our service
                val jArray = embedObject.getJSONArray("feedback")
                Log.d("ADVTECH", "feedback toimii")


                feedbacks = gson.fromJson(jArray.toString() , Array<Feedback>::class.java).toList()
                //val listView: ListView = R.id.Listview_feedbacks as ListView
                val adapter =
                    activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, feedbacks) }
                Log.d("ADVTECH", "listview toimii")
                // if in a fragment, you can use:
                // val adapter = ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1, feedbacks)
                Listview_feedbacks.adapter = adapter
                Listview_feedbacks.setAdapter(adapter)
                Log.d("ADVTECH", "listview.adapter toimii")

                // response from API, you can use this in TextView, for example
                // Check also out the example below
                // "Handling the JSON in the Volley response" for this part

                // Note: if you send data to API instead, this might not be needed

            },


            Response.ErrorListener {
                // typically this is a connection error
                Log.d("MYERROR", it.toString())
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // we have to specify a proper header, otherwise Apigility will block our queries!
                // define we are after JSON data!
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"

                /*val authorizationString = "Basic " + Base64.encodeToString(
                    (BuildConfig.APIGILITY_USER + ":" + BuildConfig.APIGILITY_PASSWORD).toByteArray(), Base64.DEFAULT
                )
                headers.put("Authorization", authorizationString);*/

                return headers
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