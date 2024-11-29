package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.OutlinedButtonComponent
import com.pubwar.quiz.ui.theme.SecondaryColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.SpojniceViewModel

@Composable
fun Spojnice(spojniceViewModel: SpojniceViewModel, userFinished: () -> Unit) {

    val currentIndex by spojniceViewModel.currentIndex.collectAsStateWithLifecycle()
    val gameIsFinished by spojniceViewModel.gameIsFinished.collectAsStateWithLifecycle()

    LaunchedEffect(gameIsFinished) {
        if (gameIsFinished)
            userFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0..<spojniceViewModel.pairs.size) {
            Row(
                Modifier.weight(1F).padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
            ) {

                val pair1 = spojniceViewModel.pairs[i]
                val pair2 = spojniceViewModel.answers[i]

                OutlinedButtonComponent(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight(),
                    text = pair1.name,
                    enabled = i <= currentIndex,
                    onClick = {},
                    style = MaterialTheme.typography.subtitle1,
                    bgColor = if(pair1.status == 0){
                        SecondaryColor
                    }else{
                        Color.Red
                    },
                    borderColor = if(i == currentIndex){
                        Yellow
                    }else{
                        Color.White
                    }
                )

                Spacer(modifier = Modifier.width(4.dp))
                OutlinedButtonComponent(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight(),
                    text = pair2.name,
                    enabled = true,
                    onClick = {
                        if(pair2.status == 0)
                        {
                            spojniceViewModel.setAnswer(i)
                        }

                    },
                    style = MaterialTheme.typography.subtitle1,
                    bgColor = if(pair2.status == 0){
                        SecondaryColor
                    }else{
                        Color.Red
                    }
                )
            }
        }
    }
}