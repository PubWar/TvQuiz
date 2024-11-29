package com.pubwar.quiz.ui.view_models

import Calculator
import androidx.lifecycle.ViewModel
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.isDigit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs

class MojBrojViewModel(game : Game?) : ViewModel() {
    val calculator = Calculator()
    val answer = MutableStateFlow("")
    val answerArray = MutableStateFlow(ArrayList<NumberItem>())
    val numbers = ArrayList<NumberItem>();
    val answerIsSend = MutableStateFlow(false)
    val operations = listOf(
        NumberItem("+"), NumberItem("-"), NumberItem("x"), NumberItem("/"), NumberItem("("),
        NumberItem(")")
    )
    val result = MutableStateFlow(0)
    val correct = MutableStateFlow(false)

    private val _game = game

    init {
        _game?.numbers?.forEach {
            numbers.add(NumberItem(it.toString()))
        }
    }


    fun setAnswer(value: NumberItem) {
        val last = answerArray.value.lastOrNull()
        if(last != null && last.value.isDigit() && value.value.isDigit())
            return

        answer.value += value.value
        value.isUsed = true
        answerArray.value.add(value)

        calculate()
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
                answer.value += it.value
            }
        }
        calculate()
    }

    private fun calculate() : Int
    {
        val expression = answer.value.replace("x", "*")
        try {
            val res = calculator.evaluate(expression);

            result.value = res.toInt()
            return abs(res.toInt() - numbers.last().value.toInt())
        }
        catch (arithmeticException: Exception){
            result.value = 0
            println(arithmeticException.message)
        }

        return 10
    }

    fun sendAnswer()
    {
        answerIsSend.value = true
        val diff = calculate()
        if(diff == 0)
        {
            _game?.message = "Ваше решење је тачно!"
            _game?.points = 10
        }
        else{
            _game?.message = "Ваше решење није тачно!"
            _game?.points =  if(diff<=5) 5 else 0
        }
    }
}

data class NumberItem(val value: String, var isUsed: Boolean = false)
