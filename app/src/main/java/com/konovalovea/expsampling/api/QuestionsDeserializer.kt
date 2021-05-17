package com.konovalovea.expsampling.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.konovalovea.expsampling.api.entities.*
import java.lang.reflect.Type

class QuestionsDeserializer : JsonDeserializer<RecordEntity> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RecordEntity {
        json!!
        val jsonArray = json.asJsonArray
        val questions = mutableListOf<QuestionEntity>()
        jsonArray.forEach { questionJsonElement ->
            val question = context?.deserialize<QuestionEntity>(
                questionJsonElement,
                QuestionEntity::class.java
            )
            if (question != null) {
                question.options = mutableListOf()
                val optionClass = when (question.questionType) {
                    "AffectGrid" -> AffectGridOptionEntity::class.java
                    "Choose" -> VerticalRadioGroupOptionEntity::class.java
                    "DiscreteSlider" -> DiscreteSliderOptionEntity::class.java
                    "Slider" -> SliderOptionEntity::class.java
                    else -> null
                }
                optionClass?.let {
                    val option = context.deserialize<OptionEntity>(questionJsonElement, it)!!
                    question.options.add(option)
                }

                questions.add(question)
            }
        }
        return RecordEntity(questions)
    }
}