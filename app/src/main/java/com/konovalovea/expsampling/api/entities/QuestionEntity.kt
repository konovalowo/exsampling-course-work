package com.konovalovea.expsampling.api.entities

import com.konovalovea.expsampling.screens.record.model.options.ListOption

class QuestionEntity(
    val id: Int,
    val questionType: String,
    val questionText: String,
    val questionSubtext: String?,
    val instructionText: String?,
    @Transient
    var options: MutableList<OptionEntity>
)