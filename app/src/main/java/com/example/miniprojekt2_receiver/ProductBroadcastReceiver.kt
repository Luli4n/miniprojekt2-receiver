package com.example.miniprojekt2_receiver

import ProductNotificationService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class ProductBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getFloatExtra("productPrice", 0.0f)
        val productQuantity = intent.getIntExtra("productQuantity", 0)

        val inputData = Data.Builder()
            .putString("productName", productName)
            .putFloat("productPrice", productPrice)
            .putInt("productQuantity", productQuantity)
            .build()

        val productNotificationWorkRequest =
            OneTimeWorkRequest.Builder(ProductNotificationService::class.java)
                .setInputData(inputData)
                .build()

        WorkManager.getInstance(context).enqueue(productNotificationWorkRequest)
    }
}