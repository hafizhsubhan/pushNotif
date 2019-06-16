package com.example.pushnotif.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Looper
import androidx.core.app.NotificationCompat
import android.R
import android.view.View
import com.example.pushnotif.MainActivity
import android.widget.Toast.makeText as makeText1


class myFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("pesan","From: " +remoteMessage!!.from!!)
        val handler = Handler(Looper
            .getMainLooper())
        handler.post(Runnable {
            Toast.makeText(applicationContext,
                remoteMessage.notification!!.body!!,Toast.LENGTH_SHORT).show()
        sendNotification(remoteMessage.notification!!.body!!)})
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.e("token", token)
        if (token != null) {
            sendRegistrationToServer(token)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        // Add custom implementation, as needed.
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "Notifikasi"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("notifikasi")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}