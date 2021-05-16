package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.screens.main.model.groups.ContactsGroup
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import com.konovalovea.expsampling.screens.main.model.groups.InfoGroup
import com.konovalovea.expsampling.screens.main.model.groups.StatsGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.TimeUnit

class DashboardRepositoryMock : DashboardRepository {

    override suspend fun getDashboard(): Dashboard? = withContext(Dispatchers.IO) {
        val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
        Dashboard(
            "День 3",
            StatsGroup(
                stats.recordsMade.toString(),
                (stats.lastRecordId + 1 - stats.recordsMade).toString(),
                (30 - stats.lastRecordId - 1).toString(),
                "10"
            ),
            InfoGroup(
                "9:00",
                "21:00"
            ),
            ContactsGroup(
                "Исследователь Имя Фамилия",
                "sample@gmail.com",
                "8 (000) 000-00-00"
            )
        )
    }
}