package com.konovalovea.expsampling.api.entities


class SignInResult(
    val token: String,
    val timeNotificationStart: NotificationTime,
    val timeNotificationEnd: NotificationTime,
    val notificationCountPerDay: Int,
    val notificationTimeout: Int
)