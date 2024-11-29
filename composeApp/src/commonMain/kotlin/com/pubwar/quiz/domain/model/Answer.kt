package com.pubwar.quiz.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = AnswerSerializer::class)
sealed class Answer {
    @Serializable
    data class OneAnswer(val answer : String, val correct : Boolean, var selected : Boolean = false, ) : Answer()

    @Serializable
    data class OrderAnswer(val answer : String, val order : Int) : Answer()
}

object AnswerSerializer : KSerializer<Answer> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Answer")

    override fun deserialize(decoder: Decoder): Answer {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JSON input")
        val jsonObject = input.decodeJsonElement().jsonObject

        // Logic to decide which subclass to instantiate based on the JSON fields
        return when {
            jsonObject.containsKey("correct") -> {
                // If 'correct' is present, it must be a OneAnswer
                Json.decodeFromJsonElement<Answer.OneAnswer>(jsonObject)
            }
            jsonObject.containsKey("order") -> {
                // If 'order' is present, it must be an OrderAnswer
                Json.decodeFromJsonElement<Answer.OrderAnswer>(jsonObject)
            }
            else -> throw SerializationException("Unknown answer type")
        }
    }

    override fun serialize(encoder: Encoder, value: Answer) {
        // Optional: Implement custom serialization logic if needed
    }
}