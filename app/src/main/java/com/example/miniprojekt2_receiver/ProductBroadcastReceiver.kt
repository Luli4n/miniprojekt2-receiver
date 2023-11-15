package com.example.miniprojekt2_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ProductBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ProductBroadcastReceiver", "Intent received: ${intent.action}")
        val newIntent = Intent(context, ProductNotificationService::class.java).apply {
            putExtra("productName", intent.getStringExtra("productName"))
            putExtra("productPrice", intent.getFloatExtra("productPrice", 0.0f))
            putExtra("productQuantity", intent.getIntExtra("productQuantity", 0))
        }
        context.startService(newIntent)
    }
}