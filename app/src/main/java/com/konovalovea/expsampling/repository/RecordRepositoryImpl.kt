package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.model.PreferenceStats
import com.konovalovea.expsampling.screens.record.model.Question
import com.konovalovea.expsampling.screens.record.model.Record
import com.konovalovea.expsampling.screens.record.model.options.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecordRepositoryImpl : RecordRepository {

    override suspend fun getRecord(): Record? {
        return try {
            val token = GlobalDependencies.INSTANCE.tokenService.getToken() ?: return null
            val questions = Api.service.getQuestions(token)?.questions
            Record(
                questions?.mapIndexed { index, questionEntity ->
                    Question(
                        "Вопрос ${index + 1}",
                        questionEntity.questionText,
                        questionEntity.options.flatMap { entityOption -> entityOption.toOptionList() },
                        questionEntity.id,
                        questionEntity.questionSubtext,
                        questionEntity.instructionText
                    )
                } ?: emptyList()
            )
        } catch (e: Exception) {
            Log.w("RecordRepositoryImpl", e)
            null
        }
    }

    override suspend fun getTutorialRecord(): Record = withContext(Dispatchers.IO) {
        Record(
            listOf(
                Question(
                    "Обучающая запись",
                    "Перед продолжением, предлагаем пройти обучающую пробу!",
                    emptyList()
                ),
                Question(
                    "Вопрос 1",
                    "Кто-то дает вам кошелек из кожи тюленя, животного редкого и вымирающего вида.",
                    listOf(
                        AffectGridOption()
                    )
                ),
                Question(
                    "Вопрос 2",
                    "Вы лежите на диване и смотрите ТВ. Вдруг вы замечаете, что по вашему запястью ползет пчела.",
                    listOf(
                        RadioGroupOption("Хорошо"),
                        RadioGroupOption("Плохо"),
                        RadioGroupOption("Красиво"),
                        RadioGroupOption("Центр"),
                        RadioGroupOption("Пчела"),
                        RadioGroupOption("Легко")
                    )
                ),
                Question(
                    "Вопрос 3",
                    "Молодая девушка собралась покончить жизнь самоубийством, прыгнув с моста. В этот момент вы проходите мимо и видите ее. Ваша реакция:",
                    listOf(
                        SliderOption("Лево", "Право"),
                        DiscreteSliderOption("Центр")
                    )
                ),
                Question(
                    "Вопрос 4",
                    "Нищий, одетый в лохмотья, пристает к вам на улице и жалобно просит денег:",
                    listOf(
                        VerticalRadioGroupOption(
                            listOf(
                                RadioOption("Я посоветую ему заняться делом."),
                                RadioOption("Куплю ему что-нибудь поесть и отведу в приют."),
                                RadioOption("Возможно я помогу ему, если мне будет жалко его.")
                            )
                        )
                    )
                )
            )
        )
    }

    override suspend fun sendAnswers(record: Record) {
        try {
            val token = GlobalDependencies.INSTANCE.tokenService.getToken() ?: return
            for (question in record.questions) {
                Api.service.sendAnswer(question.toAnswer(), token)
            }
            val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
            GlobalDependencies.INSTANCE.preferenceService.saveStats(
                PreferenceStats(stats.recordsMade + 1, stats.lastRecordId)
            )
        } catch (e: HttpException) {
            Log.w("RecordRepositoryImpl", e)
        }
    }
}