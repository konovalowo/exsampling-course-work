package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.model.PreferenceStats
import com.konovalovea.expsampling.screens.record.model.Question
import com.konovalovea.expsampling.screens.record.model.Record
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.lang.NullPointerException

class RecordRepositoryImpl : RecordRepository {

    override fun getRecord(): Single<Record> {
        return Single.create<Record> {
            val token = GlobalDependencies.INSTANCE.tokenService.getToken()
            val questions = token?.let { it1 -> Api.service.getQuestions(it1)?.questions }
            it.onSuccess(Record(
                questions?.mapIndexed { index, questionEntity ->
                    Question(
                        "Вопрос ${index + 1}",
                        questionEntity.questionText,
                        questionEntity.options.flatMap { entityOption -> entityOption.toOptionList() },
                        questionEntity.id,
                        questionEntity.questionSubtext,
                        questionEntity.instructionText
                    )
                } ?: emptyList()))
            it.onError(NullPointerException())

        }
//        return try {
//            val token = GlobalDependencies.INSTANCE.tokenService.getToken() ?: return null
//            val questions = Api.service.getQuestions(token)?.questions
//            Record(
//                questions?.mapIndexed { index, questionEntity ->
//                    Question(
//                        "Вопрос ${index + 1}",
//                        questionEntity.questionText,
//                        questionEntity.options.flatMap { entityOption -> entityOption.toOptionList() },
//                        questionEntity.id,
//                        questionEntity.questionSubtext,
//                        questionEntity.instructionText
//                    )
//                } ?: emptyList()
//            )
//        } catch (e: Exception) {
//            Log.w("RecordRepositoryImpl", e)
//            null
//        }
    }

    override fun getTutorialRecord(): Single<Record> {
        val introQuestion = Question(
            "Тестовая запись",
            "Тестовая запись знакомит с опросом, присылаемым в течение исследования. " +
                    "\nРезультат прохождения тестовой записи не будет сохранен.",
            emptyList()
        )
        return getRecord().map { Record(mutableListOf(introQuestion) + it.questions) }
    }

    override suspend fun sendAnswers(record: Record) {
        try {
            val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
            GlobalDependencies.INSTANCE.preferenceService.saveStats(
                PreferenceStats(stats.recordsMade + 1, stats.lastRecordId)
            )
            val token = GlobalDependencies.INSTANCE.tokenService.getToken() ?: return
            for (question in record.questions) {
                Api.service.sendAnswer(question.toAnswer(), token)
            }
        } catch (e: HttpException) {
            Log.w("RecordRepositoryImpl", e)
        }
    }
}