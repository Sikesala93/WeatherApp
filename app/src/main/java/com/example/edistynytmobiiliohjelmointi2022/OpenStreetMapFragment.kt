package com.example.edistynytmobiiliohjelmointi2022

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.navArgs
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataDetailBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentOpenStreetMapBinding
import org.osmdroid.config.Configuration.*
import androidx.preference.PreferenceManager
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class OpenStreetMapFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentOpenStreetMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // add a new function to activity
    fun hasPermissions(context: Context?, vararg permissions: String): Boolean = permissions.all {
        context?.let { it1 -> ActivityCompat.checkSelfPermission(it1, it) } == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenStreetMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));


        binding.map.setTileSource(TileSourceFactory.MAPNIK);

        val mapController = binding.map.controller
        mapController.setZoom(16.0)
        val startPoint = GeoPoint(66.50105053793388, 25.7275300909388);
        mapController.setCenter(startPoint);

        var firstMarker : Marker = Marker(binding.map)
        firstMarker.position = startPoint

        binding.map.overlays.add(firstMarker)

        firstMarker.setOnMarkerClickListener(Marker.OnMarkerClickListener { marker, mapView ->
            marker.showInfoWindow()
            mapView.controller.animateTo(marker.position)
            Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show()
            true
        })

        val PERMISSION_ALL = 123
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
        )
        if (!hasPermissions(context, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity as Activity, PERMISSIONS, PERMISSION_ALL)
        }


        return root
    }

    override fun onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        binding.map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        binding.map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}