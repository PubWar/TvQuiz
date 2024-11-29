package com.pubwar.quiz.ui.view_models


import androidx.lifecycle.ViewModel
import com.pubwar.quiz.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow

class KorakPoKorakViewModel(game : Game?) : ViewModel() {

    val answerIsSend = MutableStateFlow(false)
    val input = MutableStateFlow("")
    val answerIsCorrect = MutableStateFlow(false)
    val showInfo = MutableStateFlow(false)
    val points = MutableStateFlow(30)

    private val _game = game

    private val correct = game?.answer


    fun inputChar(key: String)
    {
        showInfo.value = false
        input.value += key
    }

    fun removeChar()
    {
        showInfo.value = false
        if (input.value.isNotEmpty()) input.value = input.value.dropLast(1)
    }

    fun clearAnswer()
    {
        input.value = ""
    }

    fun checkAnswer()
    {
        answerIsCorrect.value = input.value.lowercase().trim() == correct?.lowercase()?.trim()

        if(!answerIsCorrect.value)
        {
            input.value = ""
            _game?.message = "Ваше решење није тачно"
            showInfo.value = true
            points.value -= 5;
        }

        if(answerIsCorrect.value)
        {
            _game?.message = "Ваше решење је тачно"
            _game?.points = points.value
        }
        answerIsSend.value = answerIsCorrect.value
    }
}

