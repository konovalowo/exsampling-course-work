package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.ListOption
import com.konovalovea.expsampling.screens.record.model.options.SliderOption

class SliderOptionEntity(
    private val sliderMinValue: Int,
    private val sliderMaxValue: Int,
    private val leftText: String,
    private val rightText: String
) : OptionEntity {

    override fun toOptionList(): List<ListOption> {
        return listOf(
            SliderOption(
                labelLeft = leftText,
                labelRight = rightText,
                min = sliderMinValue,
                max = sliderMaxValue
            )
        )
    }
}