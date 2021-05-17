package com.konovalovea.expsampling.api.entities

class QuestionEntity(
    val id: Int,
    val questionType: String,
    val questionText: String,
    val questionSubtext: String?,
    val instructionText: String?,
    @Transient
    var options: MutableList<OptionEntity>
)