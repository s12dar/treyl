package com.lyvetech.transnature.features.tracking.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.lyvetech.transnature.core.util.Constants
import com.lyvetech.transnature.core.util.Constants.POLYLINE_COLOR
import com.lyvetech.transnature.core.util.Constants.POLYLINE_WIDTH
import com.lyvetech.transnature.databinding.FragmentTrackingBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail

class TrackingFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var binding: FragmentTrackingBinding
    private var map: GoogleMap? = null
    private lateinit var currentTrail: Trail
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var pathPoints = mutableListOf<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(requireActivity().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managePassedArguments(arguments)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onStart()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    private fun managePassedArguments(argument: Bundle?) {
        argument?.let {
            currentTrail = it.getSerializable(Constants.BUNDLE_ROUTE_KEY) as Trail
        }
    }

//    private fun setCameraViewOfMap() {
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener {
//                moveCameraToUser(it.latitude, it.longitude)
//            }
//    }
//
//    private fun moveCameraToUser(lat: Double, lng: Double) {
//        map?.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                LatLng(lat, lng),
//                MAP_ZOOM
//            )
//        )
//    }

    private fun zoomToSeePolyline() {
        val bounds = LatLngBounds.Builder()
        for (position in currentTrail.route!!) {
            bounds.include(position)
        }

        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                binding.mapView.width,
                binding.mapView.height,
                (binding.mapView.height * 0.05).toInt()
            )
        )
    }

    private fun addAllPolylines() {
        addPathPoints()
        val polylineOptions = PolylineOptions()
            .color(POLYLINE_COLOR)
            .width(POLYLINE_WIDTH)
            .addAll(pathPoints)
        map?.addPolyline(polylineOptions)
    }

    private fun addPathPoints() {
        pathPoints = currentTrail.route as MutableList<LatLng>
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.apply {
            isMyLocationEnabled = true
            setOnMyLocationButtonClickListener(this@TrackingFragment)
            setOnMyLocationClickListener(this@TrackingFragment)
        }
        addAllPolylines()
        zoomToSeePolyline()
//        setCameraViewOfMap()
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {}
}