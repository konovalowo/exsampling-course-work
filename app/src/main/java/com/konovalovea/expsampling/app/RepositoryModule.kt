package com.konovalovea.expsampling.app

import com.konovalovea.expsampling.repository.*
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun authRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun dashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository

    @Binds
    fun recordRepository(impl: RecordRepositoryImpl): RecordRepository
}