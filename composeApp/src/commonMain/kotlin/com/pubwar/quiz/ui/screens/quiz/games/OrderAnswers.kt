package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.domain.model.Answer
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.ui.components.GradientButton
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.LightBLue
import com.pubwar.quiz.ui.theme.TextColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.KoZnaZnaViewModel
import com.pubwar.quiz.ui.view_models.OrderAnswersViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.question_area
import pubwartvquiz.composeapp.generated.resources.remaining_time

@OptIn(KoinExperimentalAPI::class)
@Composable
fun OrderAnswers(game: Game?, customViewModelStoreOwner: ViewModelStoreOwner, userFinished: () -> Unit) {

    val orderAnswersViewModel: OrderAnswersViewModel = koinViewModel(
        viewModelStoreOwner = customViewModelStoreOwner,
        parameters = { parametersOf(game) }
    )

    val currentIndex by orderAnswersViewModel.currentIndex.collectAsStateWithLifecycle()
    val answerSelected by orderAnswersViewModel.answerSelected.collectAsStateWithLifecycle()
    val gameIsFinished by orderAnswersViewModel.gameIsFinished.collectAsStateWithLifecycle()
    val time by orderAnswersViewModel.timeToNext.collectAsStateWithLifecycle()
    val answers by orderAnswersViewModel.answerArray.collectAsStateWithLifecycle()

    val labels = arrayOf("A", "B", "C", "D");

    LaunchedEffect(gameIsFinished) {
        if (gameIsFinished)
            userFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            orderAnswersViewModel.questions[currentIndex].topic?.let {
                Text(
                    text = stringResource(Res.string.question_area),
                    style = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center, color = Yellow)
                )

                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle2.copy(textAlign = TextAlign.Center, color = Yellow)
                )
            }
            Spacer(Modifier.height(42.dp))
            Text(
                text = stringResource(Res.string.remaining_time),
                style = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center, color = LightBLue)
            )
            Text(
                text = time,
                style = MaterialTheme.typography.h1.copy(
                    textAlign = TextAlign.Center,
                    color = LightBLue)
            )


            Text(
                text = orderAnswersViewModel.questions[currentIndex].question,
                style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center, color = Color.White)
            )
        }

        Spacer(Modifier.weight(1f))

        AnimatedVisibility(
            visible = answerSelected
        )
        {
            Text("Tacan odgovor", style = MaterialTheme.typography.body1.copy(color = Color.White))
        }

        LazyColumn {
            items(orderAnswersViewModel.questions[currentIndex].answers.size) { i ->
                val answer = orderAnswersViewModel.questions[currentIndex].answers[i] as Answer.OrderAnswer
                key(answer.answer) {
                    GradientButton(
                        label = if(answers.contains(answer)) (answers.indexOf(answer) + 1).toString()  else "",
                        text = answer.answer,
                        textColor = TextColor,
                        gradient = AppGradients.yellowGradient,
                        onClick = {
                            orderAnswersViewModel.setAnswer(answer)
                        },
                    )
                }
                Spacer(Modifier.height(6.dp))
            }
        }

        Spacer(Modifier.height(12.dp))
        GradientButton(
            text = "Posalji odgovor",
            textColor = TextColor,
            gradient = AppGradients.yellowGradient,
            onClick = {
                orderAnswersViewModel.sendResult()
            },
        )
    }
}