package com.konovalovea.expsampling.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.konovalovea.expsampling.model.ReminderNotificationManager

val AppCompatActivity.appComponent: ApplicationComponent
    get() = (applicationContext as App).appComponent

val Fragment.appComponent: ApplicationComponent
    get() = (requireActivity().applicationContext as App).appComponent

class App : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()
        ReminderNotificationManager.createNotificationChannel(applicationContext)
    }
}