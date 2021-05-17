package com.konovalovea.expsampling.screens.main

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.konovalovea.expsampling.livedata.ConsumableValue
import com.konovalovea.expsampling.repository.DashboardRepository
import com.konovalovea.expsampling.repository.DashboardRepositoryImpl
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import com.konovalovea.expsampling.screens.record.RecordActivity
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dashboardRepository: DashboardRepository = DashboardRepositoryImpl()

    private val _state = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val mainScreenState: LiveData<MainScreenState> get() = _state

    private val _startActivityEvent: MutableLiveData<ConsumableValue<Intent>> = MutableLiveData()
    val startActivityEvent: LiveData<ConsumableValue<Intent>> get() = _startActivityEvent

    fun loadDashboard() {
        viewModelScope.launch {
            _state.value = MainScreenState.Loading
            val dashboard = dashboardRepository.getDashboard()
            if (dashboard != null) {
                _state.value = MainScreenState.Loaded(dashboard)
            } else {
                _state.value = MainScreenState.Error
            }
        }
    }

    fun onTutorialButtonClick() {
        val recordIntent = RecordActivity.getStartIntent(getApplication(), true, -1)
        _startActivityEvent.value = ConsumableValue(recordIntent)
    }
}