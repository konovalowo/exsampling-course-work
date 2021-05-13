package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.DiscreteSliderOption
import com.konovalovea.expsampling.screens.record.model.options.ListOption

class DiscreteSliderOptionEntity(
    private val discreteSliderMinValue: Int,
    private val discreteSliderMaxValue: Int,
    private val scaleTexts: List<String>
) : OptionEntity {

    override fun toOptionList(): List<ListOption> {
        return listOf(DiscreteSliderOption(
            label = scaleTexts.first(),
            min = discreteSliderMinValue,
            max = discreteSliderMaxValue
        ))
    }
}