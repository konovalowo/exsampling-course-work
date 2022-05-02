package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.screens.record.model.Record
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RecordRepository {

    fun getRecord(): Single<Record>

    fun getTutorialRecord(): Single<Record>

    fun sendAnswers(record: Record) : Completable
}