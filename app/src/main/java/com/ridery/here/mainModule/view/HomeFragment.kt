package com.ridery.here.mainModule.view

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.GpsStatus
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ridery.here.databinding.FragmentHomeBinding
import com.ridery.here.mainModule.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class HomeFragment : Fragment(), MapListener, GpsStatus.Listener{

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private val CODE_PERMISSION_LOCATION_BACKGROUND = 2106

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var mMyLocationOverlay: MyLocationNewOverlay

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.uiState.collect{uiState ->
//                    if (uiState.userExists) Log.d("TEST", "mipana sihay")
                }
            }
        }

        // TODO: Corregir y mover declaracioens y requests a patron Repositorio para cumplir Clean Architecture
        configureOSMap()

        binding.swipeRefesh.setOnRefreshListener {
            mMyLocationOverlay.runOnFirstFix {
                GlobalScope.launch(Dispatchers.Main) {
                    controller.setCenter(mMyLocationOverlay.myLocation)
                    controller.animateTo(mMyLocationOverlay.myLocation)
                    showSuccessAlert()
                }
            }
            binding.swipeRefesh.isRefreshing = false
        }

        validatePermission()
    }

    fun configureOSMap(){
        Configuration.getInstance().userAgentValue = requireContext().packageName

        mMap = binding.osMap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())

        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mMap)
        controller = mMap.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            GlobalScope.launch(Dispatchers.Main) {
                controller.setCenter(mMyLocationOverlay.myLocation)
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }
        // val mapPoint = GeoPoint(latitude, longitude)

        controller.setZoom(6.0)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)

        mMap.addMapListener(this)
    }

    fun validatePermission(){
        val permissions = arrayListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        // Segundo plano para Android Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        val permissionsArray = permissions.toTypedArray()
        if (!hasPermission(permissionsArray)) {
            askPermission(permissionsArray)
        }
    }

    private fun hasPermission(permisos: Array<String>): Boolean {
        return permisos.all {
            return ContextCompat.checkSelfPermission(
                requireActivity(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun askPermission(permisos: Array<String>) {
        requestPermissions(
            permisos,
            CODE_PERMISSION_LOCATION_BACKGROUND
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                CODE_PERMISSION_LOCATION_BACKGROUND)
        }
    }

    private fun showSuccessAlert(){
        AlertDialog.Builder(activity)
            .setTitle("Ubicación")
            .setMessage("Ubicación actualizada exitosamente.")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        binding.osMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.osMap.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        Log.e("TAG", "onCreate:la ${event?.source?.mapCenter?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.mapCenter?.longitude}")
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false
    }

    override fun onGpsStatusChanged(p0: Int) {
        Log.e("TAG", "onZoom zoom level: ${p0}")
    }
}