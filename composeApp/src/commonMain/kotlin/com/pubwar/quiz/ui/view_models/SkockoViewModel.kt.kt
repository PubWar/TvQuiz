package com.pubwar.quiz.ui.view_models


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.ui.screens.quiz.games.SkockoEnum
import com.pubwar.quiz.ui.theme.CircleBlue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SkockoViewModel(game: Game?) : ViewModel() {
    private val _game = game

     val combinations = arrayOf(
        MutableStateFlow(ArrayList<SkockoEnum>()),
        MutableStateFlow(ArrayList<SkockoEnum>()),
        MutableStateFlow(ArrayList<SkockoEnum>()),
        MutableStateFlow(ArrayList<SkockoEnum>()),
        MutableStateFlow(ArrayList<SkockoEnum>()),
        MutableStateFlow(ArrayList<SkockoEnum>())
    )

    val results = arrayOf(
        MutableStateFlow(MutableList(4) { CircleBlue }),
        MutableStateFlow(MutableList(4) { CircleBlue }),
        MutableStateFlow(MutableList(4) { CircleBlue }),
        MutableStateFlow(MutableList(4) { CircleBlue }),
        MutableStateFlow(MutableList(4) { CircleBlue }),
        MutableStateFlow(MutableList(4) { CircleBlue })
    )


    private val _currentCombinationIndex = MutableStateFlow<Int>(0)
    val currentCombinationIndex: StateFlow<Int> = _currentCombinationIndex
//    private var currentCombinationIndex = 0;

    private fun getCurrentCombination(): MutableStateFlow<ArrayList<SkockoEnum>> {
        return combinations[_currentCombinationIndex.value]
    }

    private fun getCurrentResult(): MutableStateFlow<MutableList<Color>> {
        return results[_currentCombinationIndex.value]
    }


    fun addToCombination(symbol: SkockoEnum) {
        val currentCombination = getCurrentCombination()
        if (currentCombination.value.size < 4) {
            val updatedList = currentCombination.value.toMutableList()
            updatedList.add(symbol)
            currentCombination.value = ArrayList(updatedList)
        }
    }

    fun removeFromCombination(index: Int) {
        val currentCombination = getCurrentCombination()
        if (currentCombination.value.isNotEmpty())
            if (currentCombination.value.size - 1 == index) {
                val updatedList = currentCombination.value.toMutableList()
                updatedList.removeAt(index)
                currentCombination.value = ArrayList(updatedList)
            }

    }

    fun checkAnswer(): Boolean {
        val currentCombination = getCurrentCombination()
        val result = getCurrentResult()

        if (_game?.combination != null && currentCombination.value.size == 4) {

            val comparisonResult  = compareLists(currentCombination.value, _game.combination)
            result.value = getResult(comparisonResult)

            if (comparisonResult.second == 4) {
                println("correct answer")
                _game.points = 10 - (_currentCombinationIndex.value) * 2
                _game.message = "Ваше решење је тачно"
                return true
            }
            else{
                _currentCombinationIndex.value += 1
                if(_currentCombinationIndex.value==6)
                {
                    _game.points = 0
                    _game.message = "Ваше решење није тачно"
                    return true
                }
            }
        }

        return false
    }

    private fun getResult(res: Pair<Int, Int>) : MutableList<Color> {
        val (sameItems, samePosition) = res
        val result = MutableList(4) { CircleBlue }
        // First, set the colors for the same items (Yellow)
        for (i in 0 until sameItems) {
            result[i] = Color.Yellow
        }
        // Then, override with the colors for the same position (Red)
        for (i in 0 until samePosition) {
            result[i] = Color.Red
        }
        return result
    }


    private fun compareLists(list1: List<SkockoEnum>, list2: List<SkockoEnum>): Pair<Int, Int> {
        val l1 = list1
        val l2 = list2.toMutableList()
        // Ensure both lists are of the same size for position comparison
        val minSize = minOf(l1.size, l2.size)
        // Count how many items are the same (ignoring order)
        val result = mutableListOf<SkockoEnum>()
        for (element in l1) {
            if (l2.contains(element)) {
                result.add(element)
                l2.remove(element)
            }
        }

        // Count how many items are in the same position
        var samePositionCount = 0
        for (i in 0 until minSize) {
            if (list1[i] == list2[i]) {
                samePositionCount++
            }
        }
        return Pair(result.size, samePositionCount)
    }
}