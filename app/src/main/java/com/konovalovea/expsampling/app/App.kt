package com.konovalovea.expsampling.app

import android.app.Application
import com.konovalovea.expsampling.model.ReminderNotificationManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ReminderNotificationManager.createNotificationChannel(applicationContext)
        GlobalDependencies.init(this)
    }
}