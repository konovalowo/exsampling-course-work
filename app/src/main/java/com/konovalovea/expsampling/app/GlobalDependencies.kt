package com.konovalovea.expsampling.app

import android.content.Context
import com.konovalovea.expsampling.model.ReminderAlarmManager
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.repository.TokenService

class GlobalDependencies private constructor(applicationContext: Context) {

    val tokenService by lazy { TokenService(applicationContext) }

    val reminderAlarmManger by lazy { ReminderAlarmManager(applicationContext) }

    val preferenceService by lazy { PreferenceService(applicationContext) }

    companion object {

        lateinit var INSTANCE: GlobalDependencies

        fun init(applicationContext: Context) {
            INSTANCE = GlobalDependencies(applicationContext)
        }
    }
}
