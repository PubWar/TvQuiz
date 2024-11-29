package com.pubwar.quiz.core

import com.pubwar.quiz.getCurrentTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer (
    private val coroutineScope: CoroutineScope,
    private val onTick: (Int) -> Unit // Callback invoked every second with the updated time
) {
    private var startTime: Long = 0
    private var timerJob: Job? = null
    private var isRunning: Boolean = false

    fun start(expired: Int) {
        if (isRunning) return
        isRunning = true
        startTime = getCurrentTime()
        timerJob = coroutineScope.launch {
            while (isRunning) {
                val elapsed = getCurrentTime() - startTime
                val currentTime = (elapsed / 1000).toInt() + expired
                onTick(currentTime)
                delay(1000L - (elapsed % 1000))
            }
        }
    }
    fun stop() {
        isRunning = false
        timerJob?.cancel()
    }
}