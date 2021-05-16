package com.konovalovea.expsampling.screens.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konovalovea.expsampling.screens.record.model.Record
import com.konovalovea.expsampling.screens.record.model.RecordScreenState
import com.konovalovea.expsampling.repository.RecordRepository
import com.konovalovea.expsampling.repository.RecordRepositoryMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class RecordViewModel : ViewModel() {

    private val recordRepository: RecordRepository = RecordRepositoryMock()

    var isTutorial: Boolean = false

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
        viewModelScope.launch {
            _state.value = RecordScreenState.Loading
            val record = if (isTutorial) recordRepository.getTutorialRecord() else recordRepository.getRecord()
            if (record != null) {
                this@RecordViewModel.record = record
                updateLoadedState()
            } else {
                _state.value = RecordScreenState.Error
            }
        }
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
        viewModelScope.launch {
            if (!isTutorial) {
                recordRepository.sendAnswers(record)
            }

            withContext(Dispatchers.Main) {
                _finishEvent.value = true
            }
        }
    }

    private fun updateLoadedState() {
        _state.value = RecordScreenState.Loaded(
            record.questions[currentQuestion],
            isNextAvailable,
            isPreviousAvailable
        )
    }
}