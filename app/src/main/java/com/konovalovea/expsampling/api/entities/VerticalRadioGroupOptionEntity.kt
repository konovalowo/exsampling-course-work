package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.ListOption
import com.konovalovea.expsampling.screens.record.model.options.RadioOption
import com.konovalovea.expsampling.screens.record.model.options.VerticalRadioGroupOption

class VerticalRadioGroupOptionEntity(
    private val answers: List<String>
) : OptionEntity {

    override fun toOptionList(): List<ListOption> {
        return listOf(VerticalRadioGroupOption(
            radioOptions = answers.map { RadioOption(it) }
        ))
    }
}