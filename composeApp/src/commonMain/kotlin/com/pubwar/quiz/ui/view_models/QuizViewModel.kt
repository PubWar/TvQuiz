package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.core.Timer
import com.pubwar.quiz.core.domain.onError
import com.pubwar.quiz.core.domain.onSuccess
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.ViewType
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.getCurrentTime
import com.pubwar.quiz.ui.screens.quiz.QuizState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuizViewModel(expired: Long, private val quizRepository: QuizRepository) : ViewModel() {

    private val _state = MutableStateFlow(QuizState(expired))
    val state = _state

    private val _ranking = MutableStateFlow<String?>(null)
    val ranking: StateFlow<String?> = _ranking

    private val _timeToNextGame = MutableStateFlow(state.value.timeToNextGameInSeconds)
    val timeToNextGame: StateFlow<Int> = _timeToNextGame

    private val _gameTime = MutableStateFlow(0)
    val gameTime: StateFlow<Int> = _gameTime

    private var _time = (expired / 1000).toInt()

    private val timer = Timer(viewModelScope) { updatedTime ->
        _timeToNextGame.value -= 1
        _time = updatedTime
        onTick(updatedTime)
        println("time: $_time")
    }

    private val quizId = "86f56545-a1e7-40fe-b9c5-3113ea73e03f"

    init {
        timer.start(expired)
        getQuizConfiguration(expired)

    }

    private fun getQuizConfiguration(expired: Long) = viewModelScope.launch {
            // Your coroutine code here
            quizRepository.setCurrentQuiz(quizId, getCurrentTime(), expired)
            quizRepository
                .getQuiz(quizId)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            games = response
                        )
                    }

                    state.value.games.filter { it.end <= state.value.expiredInSeconds }
                        .forEach { game ->
                            game.started = true
                            _state.update { it.copy(gameIndex = it.gameIndex + 1) }
                        }
                    setGame()
                }
                .onError {
                    println(it.name)
                }
    }

    private fun changeViewType(viewType: ViewType) {
        _state.update {
            it.copy(
                gameType = viewType
            )
        }
    }

    private fun onTick(value: Int) {
        state.value.currentGame?.let { currentGame ->
            val start = currentGame.start + state.value.delay
            val end = currentGame.end + state.value.delay

            when {
                value in start..end -> {
                    currentGame.started = true
                    changeViewType(currentGame.name)
                    _gameTime.value -= 1
                }

                value > end -> {
                    setGame()
                    changeViewType(if (state.value.currentGame != null) ViewType.REKLAME else ViewType.KRAJ)
                    if (state.value.currentGame == null) timer.stop()
                }

                value < start && state.value.gameIndex > 0 -> {
                    changeViewType(ViewType.REKLAME)
                }
            }
        }
    }

    private fun setGame() {
        if (state.value.currentGame?.started == false)
            return

        val previousState = _state.getAndUpdate { state ->
            val newIndex = state.gameIndex + 1
            state.copy(
                gameIndex = state.gameIndex + 1,
                currentGame = state.games.getOrNull(newIndex),
                totalPoints = state.games.sumOf { it.points }
            )
        }

        if (previousState.currentGame != null) {

            sendResult(previousState.currentGame)

            _state.update {
                it.copy(
                    pointsOfTheLastGame = previousState.currentGame.points,
                    pauseMessage = previousState.currentGame.message
                )
            }
        }

        if (state.value.gameIndex > 1) {
            val random = Random.Default
            val randomIntInRange = random.nextInt(774399)
            _ranking.value = "$randomIntInRange / 774399"
        }

        state.value.currentGame?.let { currentGame ->
            val start = currentGame.start + state.value.delay
            val end = currentGame.end + state.value.delay

            _gameTime.value = end - listOf(_time, start).max()  + 1
            _timeToNextGame.value = start - _time
        } ?: run {
            _gameTime.value = 0
        }
    }


    private fun sendResult(lastGame: Game) = viewModelScope.launch {
        quizRepository.sendResult(lastGame.gameId, lastGame.points, 10)
    }

    fun gamerFinishTheGame() {
        changeViewType(ViewType.REKLAME)
        setGame()
        state.value.currentGame ?: run {
            changeViewType(ViewType.KRAJ)
            timer.stop()
        }
    }
}