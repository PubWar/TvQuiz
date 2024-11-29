package com.pubwar.quiz.ui.screens.quiz

import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.ViewType

data class QuizState(
    val expired: Int,
    val showNumber: String = "9534",
    val showCycleNumber: String = "171",
    val showNumberInCycle: String = "20",
    val games: List<Game> = arrayListOf(),
    val currentGame: Game? = null,
    val gameIndex: Int = -1,
    val delay: Int = 0,
    val time: Int = expired,
    val timeToNextGame: Int = expired,
    val totalPoints: Int = 0,
    val pointsOfTheLastGame: Int = 0,
    val pauseMessage: String = "Kviz uskoro počinje!",
    val gameType: ViewType = ViewType.UVOD
)
