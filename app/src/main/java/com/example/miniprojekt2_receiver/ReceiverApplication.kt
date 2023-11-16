package com.example.miniprojekt2_receiver

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import androidx.work.WorkerFactory

class ReceiverApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().build())
    }
}