package com.konovalovea.expsampling.screens.record.model.options

class SliderOption(
    val labelLeft: String,
    val labelRight: String,
    val min: Int? = null,
    val max: Int? = null,
    var value: Int = 0
) : ListOption()