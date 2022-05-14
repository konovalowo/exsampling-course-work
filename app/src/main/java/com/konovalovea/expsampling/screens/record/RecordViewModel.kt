package com.konovalovea.expsampling.screens.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konovalovea.expsampling.repository.RecordRepository
import com.konovalovea.expsampling.repository.RecordRepositoryImpl
import com.konovalovea.expsampling.screens.BaseViewModel
import com.konovalovea.expsampling.screens.record.model.Record
import com.konovalovea.expsampling.screens.record.model.RecordScreenState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

open class RecordViewModel : ViewModel(), BaseViewModel {

    private val recordRepository: RecordRepository = RecordRepositoryImpl()

    var isTutorial: Boolean = false
    var notificationId: Int = -1

    private lateinit var record: Record

    private var currentQuestion = 0

    private val isNextAvailable: Boolean
        get() = currentQuestion < record.questions.size - 1

    private val isPreviousAvailable: Boolean
        get() = currentQuestion > 0

    private val _state = MutableLiveData<RecordScreenState>(RecordScreenState.Loading)
    val recordScreenState: LiveData<RecordScreenState> get() = _state

    private val _finishEvent = MutableLiveData<Boolean?>(null)
    val finishEvent: LiveData<Boolean?> get() = _finishEvent

    open fun loadRecord() {
        _state.value = RecordScreenState.Loading
        compositeDisposable.add(
            if (isTutorial) {
                recordRepository.getTutorialRecord()
            } else {
                recordRepository.getRecord()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        this.record = result
                        updateLoadedState()
                    },
                    {
                        _state.value = RecordScreenState.Error
                    }
                )
        )
    }

    fun onNext() {
        currentQuestion++
        updateLoadedState()
    }

    fun onPrevious() {
        currentQuestion--
        updateLoadedState()
    }

    fun onFinish() {
        if (!isTutorial) {
            compositeDisposable.add(
                recordRepository.sendAnswers(record).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _finishEvent.value = true
                    }
            )
        }
        _finishEvent.value = true
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun updateLoadedState() {
        _state.value = RecordScreenState.Loaded(
            record.questions[currentQuestion],
            isNextAvailable,
            isPreviousAvailable
        )
    }
}