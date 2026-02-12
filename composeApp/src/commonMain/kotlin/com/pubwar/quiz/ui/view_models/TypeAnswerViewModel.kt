package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.domain.model.Answer
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.Question
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.getCurrentTime
import com.pubwar.quiz.utills.addPointsBaseOnTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TypeAnswerViewModel(game: Game?, private val quizRepository: QuizRepository) : ViewModel() {

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

    private val _answerIsSent = MutableStateFlow(false)
    val answerIsSent: StateFlow<Boolean> = _answerIsSent

    private val _answerIsCorrect = MutableStateFlow(false)
    val answerIsCorrect: StateFlow<Boolean> = _answerIsCorrect

    private val _typedAnswer = MutableStateFlow("")
    val typedAnswer: StateFlow<String> = _typedAnswer

    private val _gameIsFinished = MutableStateFlow(false)
    val gameIsFinished: StateFlow<Boolean> = _gameIsFinished

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
        formatDuration(currentQuestion?.end!! - _time.value)
        if (currentQuestion.end == value) {
            _currentIndex.value = if (_currentIndex.value < questions.size - 1) {
                _currentIndex.value + 1
            } else {
                finishGame()
                return
            }
            _answerIsSent.value = false
            _typedAnswer.value = ""
        }
    }

    private fun formatDuration(seconds: Int) {
        val minutes = (seconds / 60).toString().padStart(2, '0')
        val secs = (seconds % 60).toString().padStart(2, '0')
        _timeToNext.value = "$minutes:$secs"
    }


    fun setAnswer(answer: String) {
        _typedAnswer.value = answer
    }

    private fun calculatePoints() : Int
    {
        val currentQuestion = questions.elementAtOrNull(_currentIndex.value)
        if(currentQuestion != null)
        {
            _answerIsCorrect.value = _typedAnswer.value.lowercase() == (currentQuestion.answers[0] as Answer.OneAnswer).answer.lowercase()
            if(_answerIsCorrect.value)
            {
                return 10.addPointsBaseOnTime(_game?.start ?: 0, _time.value)
            }
        }

        return  0
    }


    fun sendResult() = viewModelScope.launch {

        if(!answerIsSent.value)
        {
            _answerIsSent.value = true
            _game?.let { game ->
                val answerInSecond = _time.value - game.start
                val points = calculatePoints()

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
        _game?.message = "Osvojili ste ${_game?.points} poena"
        _gameIsFinished.value = true
    }
}
