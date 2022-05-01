package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.record.model.Record
import io.reactivex.rxjava3.core.Single

interface RecordRepository {

    fun getRecord(): Single<Record>

    fun getTutorialRecord(): Single<Record>

    suspend fun sendAnswers(record: Record)
}