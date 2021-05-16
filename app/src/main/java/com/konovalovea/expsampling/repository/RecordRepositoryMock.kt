package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.model.PreferenceStats
import com.konovalovea.expsampling.screens.record.model.options.*
import com.konovalovea.expsampling.screens.record.model.Question
import com.konovalovea.expsampling.screens.record.model.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecordRepositoryMock : RecordRepository {

    override suspend fun getRecord(): Record? = withContext(Dispatchers.IO) {
        delay(1000)
        Record(
            listOf(
                Question(
                    "Вопрос 1",
                    "Кто-то дает вам кошелек из кожи тюленя, животного редкого и вымирающего вида.",
                    listOf(
                        AffectGridOption()
                    ),
                    subtext = "Текстовая подсказка"
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

    override suspend fun sendAnswers(record: Record) = withContext(Dispatchers.IO) {
        val stats = GlobalDependencies.INSTANCE.preferenceService.getStats()
        GlobalDependencies.INSTANCE.preferenceService.run {
            saveStats(PreferenceStats(stats.recordsMade + 1, stats.lastRecordId))
        }
    }
}