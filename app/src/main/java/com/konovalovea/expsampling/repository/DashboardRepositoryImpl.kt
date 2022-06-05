package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.screens.main.model.groups.ContactsGroup
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import com.konovalovea.expsampling.screens.main.model.groups.InfoGroup
import com.konovalovea.expsampling.screens.main.model.groups.StatsGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenService: TokenService,
    private val preferenceService: PreferenceService
) : DashboardRepository {
    companion object{}


     override fun getDashboard(): Single<Dashboard> {
        return Single.create<Dashboard> {
            try {
                val userId = preferenceService.getCode()
                val project = apiService.getProject(userId!!)!!
                tokenService.saveToken(project.token)
                val daysDiff = (Date(System.currentTimeMillis()).time - project.dateStart.time)
                val endDaysDiff = (project.dateEnd.time - Date(System.currentTimeMillis()).time)
                val stats = preferenceService.getStats()
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
                                    project.timeNotificationStart.minutes.toString()
                                        .padStart(2, '0'),
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
            } catch (e: Exception) {
                it.onError(e)
            }

        }
    }
//
//    override fun getDashboard(): Single<Dashboard> {
//        val userId = preferenceService.getCode()
//         return apiService.getProject(userId!!)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                tokenService.saveToken(it.token)
//                val daysDiff = (Date(System.currentTimeMillis()).time - it.dateStart.time)
//                val endDaysDiff = (it.dateEnd.time - Date(System.currentTimeMillis()).time)
//                val stats = preferenceService.getStats()
//
//                Dashboard(
//                    "День ${TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS)}",
//                    StatsGroup(
//                        stats.recordsMade.toString(),
//                        (stats.lastRecordId + 1 - stats.recordsMade).toString(),
//                        (it.notificationCountPerDay - stats.lastRecordId - 1).toString(),
//                        "${TimeUnit.DAYS.convert(endDaysDiff, TimeUnit.MILLISECONDS)}"
//                    ),
//                    InfoGroup(
//                        "${it.timeNotificationStart.hours}:" +
//                                it.timeNotificationStart.minutes.toString()
//                                    .padStart(2, '0'),
//                        "${it.timeNotificationEnd.hours}:" +
//                                it.timeNotificationEnd.minutes.toString().padStart(2, '0')
//                    ),
//                    ContactsGroup(
//                        it.nickname,
//                        it.email,
//                        it.phoneNumber
//                    )
//                )
//            }
//    }
}