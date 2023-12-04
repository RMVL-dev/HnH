package com.example.hnhapp.mapActivity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.hnhapp.MainActivity
import com.example.hnhapp.R
import com.example.hnhapp.databinding.ActivityMapBinding
import com.example.hnhapp.presentation.contracts.MapActivityContract
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.yandex.mapkit.Animation
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import dagger.android.AndroidInjection
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    private var latitude:Double? = null
    private var longitude:Double? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    companion object{
        fun createIntent(context: Context) =
            Intent(context, MapActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getLocationPermissions()

    }

    override fun onResume() {
        super.onResume()

        getCurrentLocation()

        val inputListener = object : InputListener {
            override fun onMapLongTap(p0: Map, p1: Point) {
                setPoint(placePoint = p1)
            }

            override fun onMapTap(p0: Map, p1: Point) {
                setPoint(placePoint = p1)
            }
        }

        binding.map.mapWindow.map.addInputListener(inputListener)
        var currentGeoObject:String? = null
        binding.map.mapWindow.map.addTapListener {
            val selectionMetadata: GeoObjectSelectionMetadata = it
                .geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)
            binding.map.mapWindow.map.selectGeoObject(selectionMetadata)

            if (it.geoObject.name.isNullOrEmpty()){
                binding.tilAddress.visibility = View.GONE
            }else{
                binding.etAddress.setText(it.geoObject.name)
                binding.tilAddress.visibility = View.VISIBLE
                currentGeoObject = it.geoObject.name
            }
            true
        }

        binding.tilEnterHouse.setEndIconOnClickListener {
            startActivity(MainActivity.createStartIntent(this).putExtra(MapActivityContract.KEY, currentGeoObject))
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun getLocationPermissions(){
        if (
            (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED))
        {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
            {
                createAlertDialog()
            }else{
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
            getCurrentLocation()
        }
    }
    private fun getCurrentLocation(){
        val locationClient = LocationServices.getFusedLocationProviderClient(this)
        lifecycleScope.launch {
            if (ActivityCompat.checkSelfPermission(
                    this@MapActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@MapActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val location = locationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                ).await()
                location?.let{currentLocation ->
                    latitude = currentLocation.latitude
                    longitude = currentLocation.longitude
                    setPoint(currentLocation.latitude, currentLocation.longitude)
                }
            }
        }
    }

    private fun setPoint(latitude:Double,longitude:Double){
        binding.map.mapWindow.map.resetMapStyles()
        val placePoint = Point(latitude, longitude)
        val placeMarker = ImageProvider.fromResource(this, R.drawable.delivery_mark)
        binding.map.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = placePoint
            setIcon(placeMarker)
            direction
        }
        binding.map.mapWindow.map.move(
            CameraPosition(
                placePoint,
                /* zoom = */ 17.0f,
                /* azimuth = */ 150.0f,
                /* tilt = */ 30.0f
            ),
            Animation(Animation.Type.SMOOTH, 3f)
        ){}
    }

    private fun setPoint(placePoint: Point){
        binding.map.mapWindow.map.resetMapStyles()
        val placeMarker = ImageProvider.fromResource(this, R.drawable.delivery_mark)
        binding.map.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = placePoint
            setIcon(placeMarker)
            direction
        }
        binding.map.mapWindow.map.move(
            CameraPosition(
                placePoint,
                /* zoom = */ 17.0f,
                /* azimuth = */ 150.0f,
                /* tilt = */ 30.0f
            ),
            Animation(Animation.Type.SMOOTH, 3f)
        ){}
    }

    private fun createAlertDialog(){
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder
            .setTitle(getString(R.string.request_perm_title))
            .setMessage(getString(R.string.request_perm_body))
            .setNegativeButton(getString(R.string.request_perm_negative)) { dialog, which ->
            }
            .setPositiveButton(getString(R.string.request_perm_positive)) { dialog, which ->
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        val dialog: AlertDialog = alertDialogBuilder.create()
        dialog.show()
    }

}