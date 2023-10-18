package com.example.edistynytmobiiliohjelmointi2022

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataReadBinding

class DataReadFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentDataReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // the binding -object allows you to access views in the layout, textviews etc.

        binding.textViewInfo.text = "kotlin sanoo terve!"

        binding.buttonNavigate.setOnClickListener {
            Log.d("ADVTECH","Nappi toimii!")

            val action = DataReadFragmentDirections.actionDataReadFragmentToDataDetailFragment(id = 123445)
            it.findNavController().navigate(action)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}