package com.pubwar.quiz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
//    val id: String,
    val firstName: String,
    val lastName: String,
//    val photoUrl: String?,
//    val email: String?,
    val phoneNumber: String,
//    val city: String?,
)
