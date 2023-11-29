package com.example.hnhapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.hnhapp.MainActivity
import com.example.hnhapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar

private const val PUSH_KEY_TITLE = "HnH Application"
private const val PUSH_KEY_BODY = "HnH body"
class HnHFirebaseMessagingService: FirebaseMessagingService() {

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {

        val appName = getString(R.string.app_name)

        var title = message.data[PUSH_KEY_TITLE]
        var body = message.data[PUSH_KEY_BODY]
        var intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)

        if (title.isNullOrBlank()){
            title = message.notification?.title ?: appName
        }

        if (body.isNullOrBlank()){
            body = message.notification?.body ?: ""
        }

        if (intent == null) {
            intent = MainActivity.createStartIntent(this)
        }

        message.data.keys.forEach { key ->
            intent.putExtra(key,message.data[key])
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = when{
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> NotificationCompat.Builder(this, appName)
            else -> NotificationCompat.Builder(this)
        }

        notificationBuilder
            .setStyle(NotificationCompat.BigTextStyle(notificationBuilder).bigText(body))
            .setSmallIcon(R.drawable.error_image)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.error_image))
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build())
    }

}