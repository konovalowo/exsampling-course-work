package com.konovalovea.expsampling.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.konovalovea.expsampling.api.entities.SignInResult
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class ReminderAlarmManager @Inject constructor(private val context: Context) {

    @ExperimentalTime
    fun setNotificationsForTimeInterval(signInResult: SignInResult) {
        val notificationIntent = Intent(
            context,
            ReminderNotificationManager.NotificationBroadcastReceiver::class.java
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        repeat(200) {
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                it,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }

        fun setNotificationOnDate(calendar: Calendar, alarmId: Int) {
            notificationIntent.putExtra(
                ReminderNotificationManager.EXTRA_TIMEOUT,
                signInResult.notificationTimeout
            )
            notificationIntent.putExtra(
                ReminderNotificationManager.EXTRA_ID,
                alarmId
            )
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmId,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            Log.i(
                "ReminderAlarmManager",
                "Set alarm for ${Date(calendar.timeInMillis)} with id $alarmId"
            )
        }

        val notificationTimeStart = signInResult.timeNotificationStart
        val notificationTimeEnd = signInResult.timeNotificationEnd
        val calendarStart = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, signInResult.timeNotificationStart.hours)
            set(Calendar.MINUTE, signInResult.timeNotificationStart.minutes)
        }

        val minutesBetween =
            (notificationTimeEnd.totalMinutes - notificationTimeStart.totalMinutes) /
                    signInResult.notificationCountPerDay

        repeat(signInResult.notificationCountPerDay) { index ->
            setNotificationOnDate(calendarStart, index)
            calendarStart.add(Calendar.MINUTE, minutesBetween.toInt())
        }
    }
}