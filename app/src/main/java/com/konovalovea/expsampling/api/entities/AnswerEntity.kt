package com.konovalovea.expsampling.api.entities

import java.util.*

class AnswerEntity(
    val participantID: Int,
    val questionID: Int,
    val answerText: String,
    val isAnswered: Boolean = true,
    val id: Int = 0,
    val answerDate: String = Date(System.currentTimeMillis()).toString()
)