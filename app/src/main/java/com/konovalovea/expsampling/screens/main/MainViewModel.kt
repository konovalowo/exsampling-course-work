package com.konovalovea.expsampling.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konovalovea.expsampling.livedata.ConsumableValue
import com.konovalovea.expsampling.repository.DashboardRepository
import com.konovalovea.expsampling.repository.DashboardRepositoryMock
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import com.konovalovea.expsampling.screens.record.RecordActivity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val dashboardRepository: DashboardRepository = DashboardRepositoryMock()

    private val _state = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val mainScreenState: LiveData<MainScreenState> get() = _state

    private val _startActivityEvent: MutableLiveData<ConsumableValue<Class<*>>> = MutableLiveData()
    val startActivityEvent: LiveData<ConsumableValue<Class<*>>> get() = _startActivityEvent

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
        _startActivityEvent.value = ConsumableValue(RecordActivity::class.java)
    }
}