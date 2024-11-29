package com.pubwar.quiz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val question: String,
    val answers: ArrayList<Answer>,
    val end: Int,
)
