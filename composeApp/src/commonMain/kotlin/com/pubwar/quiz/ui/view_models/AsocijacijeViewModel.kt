package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.getCurrentTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class AsocijacijeViewModel(game: Game?) : ViewModel() {

    private val _game = game
    private val expired = game?.start!!

    private val _gameIsFinished = MutableStateFlow(false)
    val gameIsFinished: StateFlow<Boolean> = _gameIsFinished

    private val _columnState = MutableStateFlow<List<ColumnState>>(emptyList())
    val columnState: StateFlow<List<ColumnState>> = _columnState


    // Job to control the timer coroutine
    private var timerJob: Job? = null

    // Boolean to control if the timer is running
    private var isRunning: Boolean = false

    private val _time = MutableStateFlow(expired)
    val time: StateFlow<Int> = _time

    init {
        println("create AsocijacijeViewModel")

        if (_game != null) {
            _columnState.value = _game.columnStates!!
        }
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
                onTick(_time.value)
                delay(1000L - (elapsed % 1000)) //
            }
        }
    }

    private fun onTick(value: Int) {
        _columnState.value = _columnState.value.map { item ->
            if (item.openTime < value)
                ColumnState(item.columnId, item.openState, item.openTime, true)
            else
                item
        }
    }

    fun setAnswer(stateIndex: Int, answer: String) {
        val cState = _columnState.value[stateIndex]
//       if( _columnState.value[stateIndex].openState == answer)
        if (answer.lowercase() == cState.openState.lowercase()) {
            updateItemAtIndex(stateIndex)

            when (stateIndex) {
                16 -> for (i in 0..3) {
                    updateItemAtIndex(i)
                }

                17 -> for (i in 4..7) {
                    updateItemAtIndex(i)
                }

                18 -> for (i in 8..11) {
                    updateItemAtIndex(i)
                }

                19 -> for (i in 12..15) {
                    updateItemAtIndex(i)
                }

                20 -> finishGame()
            }
        }
    }


    fun updateItemAtIndex(index: Int) {
        // Get the current list
        val current = _columnState.value[index]
        val currentList = _columnState.value.toMutableList()

        // Update the item at the specified index if it's within bounds
        if (index in currentList.indices) {
            currentList[index] =
                ColumnState(current.columnId, current.openState, current.openTime, true)
            _columnState.value = currentList  // Emit the new list
        }
    }

    private fun finishGame() {
        _columnState.value = _columnState.value.map { item ->
            ColumnState(item.columnId, item.openState, item.openTime, true)
        }
//        val points = answers.fastSumBy { answer ->
//            if (answer.status == 1) 2 else 0
//        }
//
//        _game?.message = "Освојили сте $points поена"
//        _game?.points = points

        _gameIsFinished.value = true
    }

}
@Serializable
data class ColumnState(
    val columnId: String,
    val openState: String,
    val openTime: Int,
    var opened: Boolean = false
)


