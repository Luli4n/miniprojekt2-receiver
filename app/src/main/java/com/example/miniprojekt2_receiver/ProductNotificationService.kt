package com.example.miniprojekt2_receiver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ProductNotificationService : Service() {

    val channelId = "product_notification_channel"

    override fun onStartCommand( intent: Intent, flags: Int, startId: Int): Int {
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getFloatExtra("productPrice", 0.0f)
        val productQuantity = intent.getIntExtra("productQuantity", 0)
        val notificationText = "Dodano nowy produkt: $productName, Cena: $productPrice, Ilość: $productQuantity"

        createNotificationChannel()
        showNotification(notificationText)

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        // Tworzenie kanału notyfikacji dla wersji Android Oreo i nowszych
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val channelName = "Notyfikacje produktów"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Kanał dla notyfikacji produktów"
        }
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(text: String) {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Nowy Produkt Dodany")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(1, notificationBuilder.build());
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}