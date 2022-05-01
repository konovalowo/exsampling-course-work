package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.screens.main.model.groups.ContactsGroup
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import com.konovalovea.expsampling.screens.main.model.groups.InfoGroup
import com.konovalovea.expsampling.screens.main.model.groups.StatsGroup
import io.reactivex.rxjava3.core.Single
import java.lang.NullPointerException
import java.util.*
import java.util.concurrent.TimeUnit

class DashboardRepositoryImpl : DashboardRepository {

    override fun getDashboard(): Single<Dashboard> {
        return Single.create<Dashboard> {
            val userId = GlobalDependencies.INSTANCE.preferenceService.getCode()!!
            val project = Api.service.getProject(userId)!!
            GlobalDependencies.INSTANCE.tokenService.saveToken(project.token)
            val daysDiff = (Date(System.currentTimeMillis()).time - project.dateStart.time)
            val endDaysDiff = (project.dateEnd.time - Date(System.currentTimeMillis()).time)
            val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
            it.onSuccess(
                Dashboard(
                    "День ${TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS)}",
                    StatsGroup(
                        stats.recordsMade.toString(),
                        (stats.lastRecordId + 1 - stats.recordsMade).toString(),
                        (project.notificationCountPerDay - stats.lastRecordId - 1).toString(),
                        "${TimeUnit.DAYS.convert(endDaysDiff, TimeUnit.MILLISECONDS)}"
                    ),
                    InfoGroup(
                        "${project.timeNotificationStart.hours}:" +
                                project.timeNotificationStart.minutes.toString().padStart(2, '0'),
                        "${project.timeNotificationEnd.hours}:" +
                                project.timeNotificationEnd.minutes.toString().padStart(2, '0')
                    ),
                    ContactsGroup(
                        project.nickname,
                        project.email,
                        project.phoneNumber
                    )
                )
            )

            it.onError(NullPointerException())
        }
//        return try {
//            val userId = GlobalDependencies.INSTANCE.preferenceService.getCode()!!
//            val project = Api.service.getProject(userId)!!
//            GlobalDependencies.INSTANCE.tokenService.saveToken(project.token)
//            val daysDiff = (Date(System.currentTimeMillis()).time - project.dateStart.time)
//            val endDaysDiff = (project.dateEnd.time - Date(System.currentTimeMillis()).time)
//            val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
//            Dashboard(
//                "День ${TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS)}",
//                StatsGroup(
//                    stats.recordsMade.toString(),
//                    (stats.lastRecordId + 1 - stats.recordsMade).toString(),
//                    (project.notificationCountPerDay - stats.lastRecordId - 1).toString(),
//                    "${TimeUnit.DAYS.convert(endDaysDiff, TimeUnit.MILLISECONDS)}"
//                ),
//                InfoGroup(
//                    "${project.timeNotificationStart.hours}:" +
//                            project.timeNotificationStart.minutes.toString().padStart(2, '0'),
//                    "${project.timeNotificationEnd.hours}:" +
//                            project.timeNotificationEnd.minutes.toString().padStart(2, '0')
//                ),
//                ContactsGroup(
//                    project.nickname,
//                    project.email,
//                    project.phoneNumber
//                )
//            )
//        } catch (e: Exception) {
//            Log.w("DashboardRepositoryImpl", e)
//            null
//        }
    }
}