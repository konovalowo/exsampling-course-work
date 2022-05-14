package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import io.reactivex.rxjava3.core.Single

interface DashboardRepository {

    fun getDashboard(): Single<Dashboard>
}