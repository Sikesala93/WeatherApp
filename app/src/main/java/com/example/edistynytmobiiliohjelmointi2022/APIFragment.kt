package com.example.edistynytmobiiliohjelmointi2022

import Comment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentAPIBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataReadBinding
import com.google.gson.GsonBuilder


class APIFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentAPIBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: RecyclerAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAPIBinding.inflate(inflater, container, false)
        val root: View = binding.root

        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager


        getComments()



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getComments()
    {
        // this is the url where we want to get our data from
        val JSON_URL = "https://jsonplaceholder.typicode.com/comments"

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                //Log.d("ADVTECH", response)

                val gson = GsonBuilder().setPrettyPrinting().create()

                var rows : List<Comment> = gson.fromJson(response, Array<Comment>::class.java).toList()

                for(item: Comment in rows)
                {
                    Log.d("ADVTECH", item.name)
                    Log.d("ADVTECH", item.email)
                }

                val dataCount : Int = rows.size
                Log.d("ADVTECH", "Yhteensä (kpl): " + dataCount.toString())

                adapter = RecyclerAdapter(rows)
                binding.recyclerView.adapter = adapter

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
}