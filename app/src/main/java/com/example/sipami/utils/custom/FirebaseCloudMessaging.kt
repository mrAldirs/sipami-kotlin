package com.example.sipami.utils.custom

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sipami.R
import com.example.sipami.views.colleger.surat._actvShow
import com.example.sipami.views.colleger.surat._actvSurat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessaging : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = "Pengajuan Surat"
        val body = "Berhasil mengajukan surat"
        val clickAction = remoteMessage.data["click_action"]
        createNotification(title, body, clickAction)
    }

    private fun createNotification(title: String?, body: String?, clickAction: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "YourChannelId"
            val channel = NotificationChannel(channelId, "YourChannelName", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "YourChannelDescription"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, "YourChannelId")
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_notif)
            .setAutoCancel(true)

        val intent = Intent(this, _actvShow::class.java)
        intent.putExtra("id", _actvSurat().uuid)

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)

        notificationBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(0, notificationBuilder.build())
    }
}