package com.konovalovea.expsampling.screens.record.model.options

class DiscreteSliderOption(
   val label: String,
   val min: Int = 0,
   val max: Int = 10,
   var value: Int = 0
) : ListOption()