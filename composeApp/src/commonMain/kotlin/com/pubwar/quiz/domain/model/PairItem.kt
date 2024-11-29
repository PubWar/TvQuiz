package com.pubwar.quiz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PairItem(
    val name: String,
    val correctIndex: Int? = 0,
    var status: Int? = 0, // 0 - open, 1 - correct paired, 2 - disabled
    val disableTime: Int? = 0,
)
