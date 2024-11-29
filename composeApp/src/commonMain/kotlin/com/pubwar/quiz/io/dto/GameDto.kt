package com.pubwar.quiz.io.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto (
    @SerialName("key") val id: String
)

