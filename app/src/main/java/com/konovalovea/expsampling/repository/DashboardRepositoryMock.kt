package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.main.model.groups.ContactsGroup
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import com.konovalovea.expsampling.screens.main.model.groups.InfoGroup
import com.konovalovea.expsampling.screens.main.model.groups.StatsGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardRepositoryMock : DashboardRepository {

    override suspend fun getDashboard(): Dashboard? = withContext(Dispatchers.IO) {
        null
        Dashboard(
            "День 3",
            StatsGroup(
                "5",
                "2",
                "3",
                "3"
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