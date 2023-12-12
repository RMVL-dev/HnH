package com.example.hnhapp

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hnhapp.databinding.ActivityMainBinding
import com.example.hnhapp.services.ServiceWithBoth
import com.example.hnhapp.services.ServiceWithBound
import com.example.hnhapp.services.ServiceWithLaunch
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var boundService: ServiceWithBound? = null
    private var bound:Boolean = false
    private var boundBoth = false
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(ServiceWithBound.TAG, "ServiceWithBound onServiceConnected")
            boundService = ServiceWithBound()
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(ServiceWithBound.TAG, "ServiceWithBound onServiceDisconnected")
            boundService = null
            bound = false
        }
    }
    private val serviceConnectionBoth = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(ServiceWithBoth.TAG, "ServiceWithBoth onServiceConnected")
            boundService = ServiceWithBound()
            boundBoth = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(ServiceWithBoth.TAG, "ServiceWithBoth onServiceDisconnected")
            boundService = null
            boundBoth = false
        }
    }

    companion object{
        fun createStartIntent(context: Context) =
            Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //starting Service with Launch
        startService(Intent(this, ServiceWithLaunch::class.java))

        //starting service with Bound
        Intent(this, ServiceWithBound::class.java).also { intent ->
            bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
        }

        //starting service with both
        val serviceWithBothIntent = Intent(this, ServiceWithBoth::class.java)
        startService(serviceWithBothIntent)
        stopService(serviceWithBothIntent)

        serviceWithBothIntent.also { intent ->
            bindService(intent, serviceConnectionBoth, Context.BIND_AUTO_CREATE)
        }

        //fitContentViewToInsets()
        askNotificationPermission()
        getFCMToken()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
    }

    override fun onStop() {
        super.onStop()
        if (bound){
            unbindService(serviceConnection)
            bound = false
        }
        if (boundBoth){
            unbindService(serviceConnectionBoth)
            boundBoth = false
        }
    }

    private fun fitContentViewToInsets(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun askNotificationPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)||
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                ) {
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialogBuilder
                    .setTitle(getString(R.string.request_perm_title))
                    .setMessage(getString(R.string.request_perm_body))
                    .setNegativeButton(getString(R.string.request_perm_negative)) { dialog, which ->

                    }
                    .setPositiveButton(getString(R.string.request_perm_positive)) { dialog, which ->
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.POST_NOTIFICATIONS,
                                Manifest.permission.CAMERA
                            )
                        )
                    }
                val dialog: AlertDialog = alertDialogBuilder.create()
                dialog.show()
            }
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.CAMERA
                )
            )
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                Log.d("TOKEN", task.result)
            }
        )
    }
}