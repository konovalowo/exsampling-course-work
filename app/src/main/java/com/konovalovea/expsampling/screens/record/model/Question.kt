package com.konovalovea.expsampling.screens.record.model

import com.konovalovea.expsampling.api.entities.AnswerEntity
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.record.model.options.*

class Question(
    val header: String,
    val text: String,
    val options: List<ListOption>,
    val id: Int = 0,
    val subtext: String? = null,
    val hint: String? = null
) {

    fun toAnswer(preferenceService: PreferenceService): AnswerEntity {
        var answerText = ""
        for (option in options) {
            when (option) {
                is AffectGridOption -> answerText += "${option.valueX}:${option.valueY}"
                is DiscreteSliderOption -> answerText += option.value
                is RadioGroupOption -> answerText += option.value
                is SliderOption -> answerText += option.value
                is VerticalRadioGroupOption -> answerText += option.value
            }
            answerText += ";"
        }
        answerText = answerText.trim(';')
        return AnswerEntity(
            participantID = preferenceService.getCode()!!.toInt(),
            questionID = this.id,
            answerText = answerText
        )
    }
}