package com.example.edistynytmobiiliohjelmointi2022

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataDetailBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var gMap : GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->


        //save map object for later use
        gMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        var marker1 : Marker = googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        marker1.tag = "Sydney"

        val rovaniemi = LatLng(66.5026315631997, 25.73057809750101)
        var marker2 : Marker = googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Rovaniemen keskusta"))
        marker2.tag = "Rovaniemi"

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 17.0f))


        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(rovaniemi))

        googleMap.setOnMarkerClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val zoomControls : CheckBox = binding.checkBoxZoomcontrols

        zoomControls.setOnCheckedChangeListener { buttonView, isChecked ->
            //Toast.makeText(context,isChecked.toString(),Toast.LENGTH_SHORT).show()


                gMap.uiSettings.isZoomControlsEnabled = isChecked
        }

        val maptypeControls : RadioGroup = binding.radioGroupMaptype

        maptypeControls.setOnCheckedChangeListener { buttonView, isChecked ->
            //Toast.makeText(context,isChecked.toString(),Toast.LENGTH_SHORT).show()
            if (binding.radioButtonNormalMap.isChecked)
            {
                Log.d("ADVTECH","Normal map")
                gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            if (binding.radioButtonSatelliteMap.isChecked)
            {
                Log.d("ADVTECH","Satellite map")
                gMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            if (binding.radioButtonHybridMap.isChecked)
            {
                Log.d("ADVTECH","Hybrid map")
                gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }


        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.d("ADVTECH","Marker click!")
            Log.d("ADVTECH", p0?.title.toString())

        val city : String = p0?.tag.toString()
        Log.d("ADVTECH", city)

        val action = MapsFragmentDirections.actionMapsFragmentToCityWeatherFragment(city)
        findNavController().navigate(action)


        return false
    }


}