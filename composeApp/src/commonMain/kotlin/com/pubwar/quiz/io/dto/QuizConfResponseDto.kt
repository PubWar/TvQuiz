package com.pubwar.quiz.io.dto

import com.pubwar.quiz.domain.model.Game
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizConfResponseDto(
    @SerialName("games") val games: List<Game>
)
