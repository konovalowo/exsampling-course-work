package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.ListOption

interface OptionEntity {

    fun toOptionList(): List<ListOption>
}