package com.example.hnhapp.mapActivity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.hnhapp.R
import com.example.hnhapp.databinding.ActivityMapBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
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

        val tapListener = MapObjectTapListener { mapObject, point ->
            setPoint(point)
            true
        }
        binding.map.mapWindow.map.addTapListener {
            binding.map.mapWindow.map.deselectGeoObject()
            val geoObject = it.geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)
            binding.map.mapWindow.map.selectGeoObject(geoObject)
            false
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
        }
        if (
            (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            lifecycleScope.launch {
                val location = LocationServices.getFusedLocationProviderClient(this@MapActivity).getCurrentLocation(
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