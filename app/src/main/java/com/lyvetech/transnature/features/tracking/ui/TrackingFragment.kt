package com.lyvetech.transnature.features.tracking.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants
import com.lyvetech.transnature.core.util.Constants.ACTION_PAUSE_SERVICE
import com.lyvetech.transnature.core.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.lyvetech.transnature.core.util.Constants.ACTION_STOP_SERVICE
import com.lyvetech.transnature.core.util.Constants.POLYLINE_COLOR
import com.lyvetech.transnature.core.util.Constants.POLYLINE_WIDTH
import com.lyvetech.transnature.core.util.LocationUtils
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentTrackingBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.tracking.domain.service.TrackingService
import com.lyvetech.transnature.features.tracking.domain.service.myPolyline
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var binding: FragmentTrackingBinding
    private var map: GoogleMap? = null
    private lateinit var currentTrail: Trail
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var pathPoints = mutableListOf<LatLng>()

    private var currTimeMillis = 0L
    private var mIsTracking = false
    private var mPathPoints = mutableListOf<myPolyline>()

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
        (activity as OnboardingUtils).hideTopAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managePassedArguments(arguments)
        manageBindingViews()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        subscribeToObservers()
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

    private fun manageBindingViews() {
        with(binding) {
            fabRoute.setOnClickListener {
                zoomToSeePolyline()
            }

            fabStart.setOnClickListener {
                startOrResumeSession()
            }

            btnFinish.setOnClickListener {
                endSessionAndSaveToDb()
            }

            btnPause.setOnClickListener {
                if (!mIsTracking) {
                    startOrResumeSession()
                } else {
                    pauseSession()
                }
            }

        }
    }

    private fun zoomToSeePolyline() {
        val bounds = LatLngBounds.Builder()
        for (position in currentTrail.route!!) {
            bounds.include(position)
        }

        map?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                binding.mapView.width,
                binding.mapView.height,
                (binding.mapView.height * 0.05).toInt()
            )
        )
    }

    private fun addTrailPolylines() {
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

    private fun addStartAndEndMarkers() {
        val startingPoint = LatLng(
            currentTrail.startLatitude,
            currentTrail.startLongitude
        )
        val endPoint = LatLng(
            currentTrail.endLatitude,
            currentTrail.endLongitude
        )

        map?.addMarker(
            MarkerOptions()
                .position(startingPoint)
                .title(R.string.title_starting_point.toString())
                .icon(getBitmapDescriptorFromVector(R.drawable.ic_start_24dp))
        )
        map?.addMarker(
            MarkerOptions()
                .position(endPoint)
                .title(R.string.title_ending_point.toString())
                .icon(getBitmapDescriptorFromVector(R.drawable.ic_finish_24dp))
        )
    }

    private fun getBitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(requireContext(), vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                intrinsicHeight,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.apply {
            isMyLocationEnabled = true
            setOnMyLocationButtonClickListener(this@TrackingFragment)
            setOnMyLocationClickListener(this@TrackingFragment)
        }
        addTrailPolylines()
        addUserPolylines()
        addStartAndEndMarkers()
        zoomToSeePolyline()
    }

    private fun sendCommandToTrackingService(sendAction: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = sendAction
            requireContext().startService(it)
        }

    private fun startOrResumeSession() {
        with(binding) {
            fabStart.visibility = View.INVISIBLE
            btnFinish.visibility = View.VISIBLE
            btnPause.visibility = View.VISIBLE
        }

        if (!mIsTracking) {
            sendCommandToTrackingService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun pauseSession() {
        sendCommandToTrackingService(ACTION_PAUSE_SERVICE)
    }

    private fun stopSession() {
        sendCommandToTrackingService(ACTION_STOP_SERVICE)
    }

    private fun updateTracking(isTracking: Boolean) {
        mIsTracking = isTracking
        if (!isTracking) {
            with(binding) {
                btnPause.text = getString(R.string.btn_resume)
                btnFinish.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                btnPause.text = getString(R.string.btn_pause)
            }
        }
    }

    private fun endSessionAndSaveToDb() {
        map?.snapshot {
            var distanceInMeters = 0
            for (polyline in mPathPoints) {
                distanceInMeters += LocationUtils.calculatePolylineLength(polyline).toInt()
            }
            val averageSpeed =
                round((distanceInMeters / 1000f) / (currTimeMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
//            val caloriesBurned = ((distanceInMeters / 1000f) * mWeight).toInt()
//            val run = Run(
//                it,
//                dateTimestamp,
//                averageSpeed,
//                distanceInMeters,
//                currTimeMillis,
//                caloriesBurned
//            )
//            viewModel.insertRun(run)
            Snackbar.make(
                requireView(),
                R.string.txt_trail_saved,
                Snackbar.LENGTH_SHORT
            ).show()
            stopSession()
        }
    }

    private fun addUserPolylines() {
        for (polyline in mPathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it)
        }

        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            mPathPoints = it
            addLatestUserPolyline()
        }

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            currTimeMillis = it
            val formattedTime = LocationUtils.getFormattedStopWatchTime(currTimeMillis, true)
            binding.tvTimer.text = formattedTime
        }
    }

    private fun addLatestUserPolyline() {
        if (mPathPoints.isNotEmpty() && mPathPoints.last().size > 1) {
            val preLastLatLng =
                mPathPoints.last()[mPathPoints.last().size - 2] // second last element of last polyline in mPathPoints list
            val lastLatLng =
                mPathPoints.last().last() // last element of polyline in mPathPoints list
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {}
}