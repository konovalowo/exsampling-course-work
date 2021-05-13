package com.konovalovea.expsampling.screens.record.model

sealed class RecordScreenState {

    object Loading : RecordScreenState()

    class Loaded(
        val question: Question,
        val isNextAvailable: Boolean,
        val isPreviousAvailable: Boolean
    ) : RecordScreenState()

    object Error : RecordScreenState()
}