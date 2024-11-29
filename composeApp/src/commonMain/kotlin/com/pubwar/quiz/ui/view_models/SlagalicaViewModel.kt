package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import com.pubwar.quiz.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow

class SlagalicaViewModel(game: Game?) : ViewModel() {
    val answer = MutableStateFlow("")
    val answerArray = MutableStateFlow(ArrayList<LetterItem>())
    val letters = ArrayList<LetterItem>();
    val answerIsSend = MutableStateFlow(false)
    private val _game = game

    init {
        println("create SlagalicaViewModel")
        _game?.letters?.forEach {
            letters.add(LetterItem(it))
        }

//        lettersString?.forEach {
//            letters.add(LetterItem(it))
//        }
    }

    fun setAnswer(value: LetterItem) {
        answer.value += value.letter
        value.isUsed = true
        answerArray.value.add(value)
    }

    fun backspace()
    {
        println("answer length " + answerArray.value.size)
        val last = answerArray.value.lastOrNull()
        if(last !=null)
        {
            last.isUsed = false
            answerArray.value.removeLast()
            answer.value = ""
            answerArray.value.forEach {
                answer.value += it.letter
            }
        }

    }

    fun sendAnswer()
    {
        _game?.points = answerArray.value.size * 2
        _game?.message = "Ваша реч је прихваћена"
        answerIsSend.value = true

    }
}

data class LetterItem(val letter: String, var isUsed: Boolean = false)