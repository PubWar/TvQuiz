package com.pubwar.quiz.io.dto

import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.utills.decrypt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class QuizConfResponseDto(
    @SerialName("games") val games: List<Game>,
    @SerialName("quizId") val quizId: String
)