package com.konovalovea.expsampling.model

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.repository.PreferenceService
import java.util.concurrent.TimeUnit

object ReminderNotificationManager {

    private const val CHANNEL_ID = "reminder_notification"
    const val EXTRA_TIMEOUT = "timeout"
    const val EXTRA_ID = "id"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.filling_in)
            val descriptionText = context.getString(R.string.fill_in_notification)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    class NotificationBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val timeout = intent?.getIntExtra(EXTRA_TIMEOUT, DEFAULT_TIMEOUT)!!
            val id = intent.getIntExtra(EXTRA_ID, -1)
            val preferenceService = PreferenceService(context)
            preferenceService.apply {
                val stats = getStats()
                saveStats(PreferenceStats(stats.recordsMade, id))
            }
            val notification: Notification? = buildNotification(context, timeout.toLong())
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        private fun buildNotification(context: Context, timeout: Long): Notification {
            return NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.reminder_notification_title))
                .setContentText(context.getString(R.string.reminder_notification_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setTimeoutAfter(TimeUnit.MINUTES.toMillis(timeout))
                .build()
        }

        companion object {
            private const val NOTIFICATION_ID = 1
            private const val DEFAULT_TIMEOUT = 20
        }
    }
}