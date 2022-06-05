package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

interface DashboardRepository {

    fun getDashboard(): Single<Dashboard>
    companion object{}
}