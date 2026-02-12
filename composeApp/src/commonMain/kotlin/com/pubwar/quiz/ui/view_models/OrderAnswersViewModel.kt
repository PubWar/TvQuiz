package com.pubwar.quiz.ui.view_models

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.domain.model.Answer
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.Question
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.getCurrentTime
import com.pubwar.quiz.utills.addPointsBaseOnTime
import com.pubwar.quiz.utills.formatDuration
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderAnswersViewModel(game: Game?, private val quizRepository: QuizRepository) : ViewModel() {

    private var timerJob: Job? = null

    // Boolean to control if the timer is running
    private var isRunning: Boolean = false


    private val expired: Int = game?.start ?: 0

    private val _time = MutableStateFlow(expired)
    val time: StateFlow<Int> = _time

    private val _timeToNext = MutableStateFlow("00:00")
    val timeToNext: StateFlow<String> = _timeToNext

    private val _game = game

    var questions = ArrayList<Question>()


    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _answerIsSend = MutableStateFlow(false)
    val answerIsSend: StateFlow<Boolean> = _answerIsSend

    private val _answerSelected = MutableStateFlow(false)
    val answerSelected: StateFlow<Boolean> = _answerSelected

    private val _gameIsFinished = MutableStateFlow(false)
    val gameIsFinished: StateFlow<Boolean> = _gameIsFinished

    private val _answerArray = MutableStateFlow(ArrayList<Answer.OrderAnswer>())
    val answerArray: StateFlow<ArrayList<Answer.OrderAnswer>> = _answerArray

    init {
        questions = game?.questions as ArrayList<Question>
        startTimer()
    }

    private fun startTimer() {
        if (isRunning) return // Prevent starting a new timer if one is already running
        // Launch a coroutine
        isRunning = true
        timerJob = viewModelScope.launch {
            val startTime = getCurrentTime()
            while (isRunning) {
                val elapsed = getCurrentTime() - startTime
                _time.value = (elapsed / 1000).toInt() + expired

//                println("time value: ${_time.value.toString()}")
                onTick(_time.value)
                delay(1000L - (elapsed % 1000)) //
            }
        }
    }

    private fun onTick(value: Int) {
        val currentQuestion = questions.elementAtOrNull(_currentIndex.value)
//        if (currentQuestion?.end == value || _answerSelected.value) {
        _timeToNext.value = (currentQuestion?.end!! - _time.value).formatDuration()

        if (currentQuestion.end == value) {
            _currentIndex.value = if (_currentIndex.value < questions.size - 1) {
                _currentIndex.value + 1
            } else {
                finishGame()
                return
            }
            _answerSelected.value = false
        }
    }


    fun setAnswer(answer: Answer.OrderAnswer) {

        if (_answerArray.value.contains(answer)) {
            _answerArray.value = ArrayList(_answerArray.value).apply { remove(answer) }
        } else {
            _answerArray.value = ArrayList(_answerArray.value).apply { add(answer) }
        }

        if (_answerArray.value.size == questions[_currentIndex.value].answers.size) {
            _answerSelected.value = true
            _answerArray.value.forEachIndexed { index, value ->
                println("Index: $index, Value: $value")
                if (index + 1 != value.order) {
                    _answerSelected.value = false
                }
            }
        } else {
            _answerSelected.value = false
        }

//        if (!_answerSelected.value) {
//            answer.selected = true
//            _answerSelected.value = true
//        }
//        answer.selected = _answerSelected.value.not()
//        _answerSelected.value = _answerSelected.value.not()
    }

    private fun checkAnswer(): Boolean {
        _answerArray.value.forEachIndexed { index, value ->
            if (index + 1 != value.order)
                return false
        }
        return true
    }

    fun sendResult() = viewModelScope.launch {

        if(!answerIsSend.value){
            _answerIsSend.value = true
            _game?.let { game ->
                val answerInSecond = _time.value - game.start

                val points = if (checkAnswer()) 10.addPointsBaseOnTime(_game.start, _time.value) else 0

                game.message = "Osvojili ste $points poena"
                game.points = points

                println("send result to server")
                quizRepository.sendResult(game.gameId, points, answerInSecond)
                finishGame()
            }
        }

    }


    private fun finishGame() {
        isRunning = false
        timerJob?.cancel()
        _gameIsFinished.value = true
    }
}
