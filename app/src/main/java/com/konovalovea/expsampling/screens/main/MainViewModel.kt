package com.konovalovea.expsampling.screens.main

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.konovalovea.expsampling.livedata.ConsumableValue
import com.konovalovea.expsampling.repository.DashboardRepository
import com.konovalovea.expsampling.screens.BaseViewModel
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import com.konovalovea.expsampling.screens.record.RecordActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    private val dashboardRepository: DashboardRepository
) : AndroidViewModel(application), BaseViewModel {

    private val _state = MutableLiveData<MainScreenState>(MainScreenState.Loading)
    val mainScreenState: LiveData<MainScreenState> get() = _state

    private val _startActivityEvent: MutableLiveData<ConsumableValue<Intent>> = MutableLiveData()
    val startActivityEvent: LiveData<ConsumableValue<Intent>> get() = _startActivityEvent

    fun loadDashboard(): Any? {
        _state.value = MainScreenState.Loading
        compositeDisposable.add(
            dashboardRepository.getDashboard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _state.value = MainScreenState.Loaded(result)
                    Log.d("TAG_VM", "loadDashboard1 ")
                }) {
                    _state.value = MainScreenState.Error
                    Log.d("TAG_VM", "loadDashboard2")
                }
        )

        return null
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