package com.pubwar.quiz.domain.model

import com.pubwar.quiz.ui.screens.quiz.games.SkockoEnum
import com.pubwar.quiz.ui.view_models.ColumnState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


enum class ViewType {
    UVOD,
    REKLAME,
    SLAGALICA,
    MOJ_BROJ,
    KORAK_PO_KORAK,
    SKOCKO,
    SPOJNICE,
    KO_ZNA_ZNA,
    ASOCIJACIJE,
    TYPE_ANSWER,
    ORDER_ANSWERS,
    KRAJ
}
@Serializable
data class Game(
    val gameId : Int,
    @SerialName("viewType")
    val name: ViewType,
    val start: Int,
    val end: Int,
    var points: Int = 0,
    var area: String? = "",
    var message: String = "Време је истекло!",
    val questions: ArrayList<Question>? = null,
    val letters: ArrayList<String>? = null,
    val numbers: ArrayList<Int>? = null,
    val answer: String? = null,
    val combination: ArrayList<SkockoEnum>? = null,
    var started: Boolean = false,
    var pairList: List<PairItem>? = null,
    var pairAnswers: List<PairItem>? = null,
    var columnStates: List<ColumnState>? = null
)