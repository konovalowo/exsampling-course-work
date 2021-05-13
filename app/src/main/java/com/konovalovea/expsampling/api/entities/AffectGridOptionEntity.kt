package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.AffectGridOption
import com.konovalovea.expsampling.screens.record.model.options.ListOption

class AffectGridOptionEntity(
    private val delimiterCount: Int
) : OptionEntity {

    override fun toOptionList(): List<ListOption> =
        listOf(AffectGridOption(delimiterCount = this.delimiterCount))
}