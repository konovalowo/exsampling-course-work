package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.record.model.Record

interface RecordRepository {

    suspend fun getRecord(): Record?

    suspend fun getTutorialRecord(): Record

    suspend fun sendAnswers(record: Record)
}