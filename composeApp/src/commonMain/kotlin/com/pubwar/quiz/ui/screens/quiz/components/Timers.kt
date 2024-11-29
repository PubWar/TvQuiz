package com.pubwar.quiz.ui.screens.quiz.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.domain.model.ViewType
import com.pubwar.quiz.ui.components.AutoSizeText
import com.pubwar.quiz.ui.view_models.QuizViewModel

@Composable
fun Timers(viewModel: QuizViewModel, viewType: ViewType) {
    val gameTime by viewModel.gameTime.collectAsStateWithLifecycle()
    val timeToNextGame by viewModel.timeToNextGame.collectAsStateWithLifecycle()
    Row(
        modifier = Modifier,
    ) {
        if(viewType in listOf(ViewType.REKLAME, ViewType.UVOD) && timeToNextGame > 0){
            AutoSizeText(   timeToNextGame.toString(), textStyle = MaterialTheme.typography.h3,)
        }
        else if(viewType !in listOf(ViewType.REKLAME, ViewType.UVOD, ViewType.KRAJ))
        {
            AutoSizeText(   gameTime.toString(), textStyle = MaterialTheme.typography.h3,)
        }
    }
}