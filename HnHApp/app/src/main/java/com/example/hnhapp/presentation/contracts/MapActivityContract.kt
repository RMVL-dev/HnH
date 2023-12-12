package com.example.hnhapp.presentation.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.hnhapp.mapActivity.MapActivity

class MapActivityContract: ActivityResultContract<String, String?>() {

    companion object{
        const val KEY = "GEO"
    }

    override fun createIntent(context: Context, input: String): Intent =
        Intent(context, MapActivity::class.java)
            .putExtra(KEY, "value")

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        when{
            resultCode != Activity.RESULT_OK -> null
            else -> intent?.getStringExtra(KEY)
        }
}