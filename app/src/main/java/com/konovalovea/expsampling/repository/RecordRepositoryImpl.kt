package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.model.PreferenceStats
import com.konovalovea.expsampling.screens.record.model.Question
import com.konovalovea.expsampling.screens.record.model.Record
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenService: TokenService,
    private val preferenceService: PreferenceService
) : RecordRepository {

    override fun getRecord(): Single<Record> {
        return Single.create<Record> {
            try {
                val token = tokenService.getToken()
                val questions = token?.let { it1 -> apiService.getQuestions(it1)?.questions }
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
            } catch (e: Exception) {
                it.onError(e)
            }

        }
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

    override fun sendAnswers(record: Record): Completable {
        return Completable.create {
            val stats = preferenceService.getStats()
            preferenceService.saveStats(
                PreferenceStats(stats.recordsMade + 1, stats.lastRecordId)
            )

            val token = tokenService.getToken()

            for (question in record.questions) {
                token?.let { it1 ->
                    apiService.sendAnswer(question.toAnswer(preferenceService), it1)
                }
            }
        }
    }
}