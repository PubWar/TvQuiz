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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KoZnaZnaViewModel(game: Game?, private val quizRepository: QuizRepository) : ViewModel() {

    private var timerJob: Job? = null

    // Boolean to control if the timer is running
    private var isRunning: Boolean = false


    private var expired: Int = game?.start ?: 0

    private val _time = MutableStateFlow(expired)
    val time: StateFlow<Int> = _time

    private val _timeToNext = MutableStateFlow("00:00")
    val timeToNext: StateFlow<String> = _timeToNext

    private var _game = game

    var questions = ArrayList<Question>()


    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _answerIsSend = MutableStateFlow(false)
    val answerIsSend: StateFlow<Boolean> = _answerIsSend

    private val _answerSelected = MutableStateFlow(false)
    val answerSelected: StateFlow<Boolean> = _answerSelected

    private val _gameIsFinished = MutableStateFlow(false)
    val gameIsFinished: StateFlow<Boolean> = _gameIsFinished


    private val _selectedAnswer = MutableStateFlow<Answer.OneAnswer?>(null)
    val selectedAnswer: StateFlow<Answer.OneAnswer?> = _selectedAnswer
    init {
//        println("KO ZNA ZNA VIEW MODEL IS CREATED")
//        questions = game?.questions as ArrayList<Question>
//        startTimer()

        initViewModel()
    }

    fun initViewModel()
    {
        println("KO ZNA ZNA VIEW MODEL IS CREATED")
        questions = _game?.questions as ArrayList<Question>
        startTimer()
    }

    fun updateGame(game: Game?)
    {
        expired = game?.start ?: 0
        _game = game
        initViewModel()
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
            _answerSelected.value = false
        }
    }

    private fun formatDuration(seconds: Int) {
        val minutes = (seconds / 60).toString().padStart(2, '0')
        val secs = (seconds % 60).toString().padStart(2, '0')
        _timeToNext.value = "$minutes:$secs"
    }



    fun setAnswer(answer: Answer.OneAnswer) {
        if (!answerIsSend.value) {
            val currentQuestion = questions.elementAtOrNull(_currentIndex.value)

            currentQuestion?.answers?.mapNotNull { it as? Answer.OneAnswer }
                ?.forEach { it.selected = false }
            answer.selected = true
//            _selectedAnswer.value = answer
        }
    }

    fun sendResult() = viewModelScope.launch {

        if(!answerIsSend.value){
            _game?.let { game ->
                _answerIsSend.value = true
                val answerInSecond = _time.value - game.start

                val points = questions.fastSumBy { question ->
                    question.answers.firstOrNull { (it as Answer.OneAnswer).selected }?.let {
                        if ((it as Answer.OneAnswer).correct)
                            10.addPointsBaseOnTime(game.start, _time.value) else 0
                    } ?: 0
                }

//                val p = if(selectedAnswer.value?.selected == true)
//                            10.addPointsBaseOnTime(game.start, _time.value) else 0

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
