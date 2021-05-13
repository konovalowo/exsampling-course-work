package com.konovalovea.expsampling.api.entities

import java.util.*

class Project(
    val token: String,
    val dateStart: Date,
    val dateEnd: Date,
    val notificationCountPerDay: Int,
    val timeNotificationStart: NotificationTime,
    val timeNotificationEnd: NotificationTime,
    val nickname: String,
    val email: String,
    val phoneNumber: String
)