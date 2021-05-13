package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.main.model.groups.Dashboard

interface DashboardRepository {

    suspend fun getDashboard(): Dashboard?
}