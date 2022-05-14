package com.konovalovea.expsampling.screens.main

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.konovalovea.expsampling.livedata.ConsumableValue
import com.konovalovea.expsampling.repository.DashboardRepository
import com.konovalovea.expsampling.repository.DashboardRepositoryImpl
import com.konovalovea.expsampling.screens.BaseViewModel
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import com.konovalovea.expsampling.screens.record.RecordActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application), BaseViewModel {

    private val dashboardRepository: DashboardRepository = DashboardRepositoryImpl()

    private val _state = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val mainScreenState: LiveData<MainScreenState> get() = _state

    private val _startActivityEvent: MutableLiveData<ConsumableValue<Intent>> = MutableLiveData()
    val startActivityEvent: LiveData<ConsumableValue<Intent>> get() = _startActivityEvent

    fun loadDashboard() {
        _state.value = MainScreenState.Loading
        compositeDisposable.add(
            dashboardRepository.getDashboard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _state.value = MainScreenState.Loaded(result)
                }) {
                    _state.value = MainScreenState.Error
                }
        )
    }

    fun onTutorialButtonClick() {
        val recordIntent = RecordActivity.getStartIntent(getApplication(), true, -1)
        _startActivityEvent.value = ConsumableValue(recordIntent)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}