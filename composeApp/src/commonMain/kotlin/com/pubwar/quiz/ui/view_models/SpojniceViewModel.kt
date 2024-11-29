package com.pubwar.quiz.ui.view_models

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.PairItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SpojniceViewModel(game: Game?) : ViewModel() {
    private val _game = game

    var pairs = ArrayList<PairItem>()
    var answers = ArrayList<PairItem>()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _gameIsFinished = MutableStateFlow(false)
    val gameIsFinished: StateFlow<Boolean> = _gameIsFinished

    init {
        println("create SpojniceViewModel")
        pairs = game?.pairList as ArrayList<PairItem>
        answers = game.pairAnswers as ArrayList<PairItem>
    }

    fun setAnswer(index : Int)
    {
        if(pairs[_currentIndex.value].correctIndex == index)
        {
            answers[index].status = 1
            pairs[_currentIndex.value].status = 1
        }

        _currentIndex.value = if (_currentIndex.value < 9) {
            _currentIndex.value + 1
        } else {
            finishGame()
            return
        }
    }

    private fun finishGame()
    {
        val points = answers.fastSumBy { answer ->
            if (answer.status == 1) 2 else 0
        }

        _game?.message = "Освојили сте $points поена"
        _game?.points = points

        _gameIsFinished.value = true
    }

}
