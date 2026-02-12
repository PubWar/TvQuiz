package com.pubwar.quiz.ui.screens.quiz.games

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pubwar.quiz.domain.model.Answer
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.ui.components.GradientButton
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.LightBLue
import com.pubwar.quiz.ui.theme.TextColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.KoZnaZnaViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.KoinViewModelFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.question_area
import pubwartvquiz.composeapp.generated.resources.remaining_time

@OptIn(KoinExperimentalAPI::class)
@Composable
fun KoZnaZna(game: Game?, customViewModelStoreOwner:ViewModelStoreOwner, userFinished: () -> Unit) {


    // Use koinViewModel with the custom ViewModelStoreOwner
    val koZnaZnaViewModel: KoZnaZnaViewModel = koinViewModel(
        viewModelStoreOwner = customViewModelStoreOwner,
        parameters = { parametersOf(game) }
    )
        // Inject the ViewModel
//        val koZnaZnaViewModel: KoZnaZnaViewModel = koinViewModel(parameters = { parametersOf(game) })

        val currentIndex by koZnaZnaViewModel.currentIndex.collectAsStateWithLifecycle()
        val answerSelected by koZnaZnaViewModel.answerSelected.collectAsStateWithLifecycle()
        val gameIsFinished by koZnaZnaViewModel.gameIsFinished.collectAsStateWithLifecycle()
        val time by koZnaZnaViewModel.timeToNext.collectAsStateWithLifecycle()

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
//        Image(
//            painter = painterResource(Res.drawable.main_icon),
//            contentDescription = "skocko",
//            modifier = Modifier
//                .width(110.dp)
//                .height(165.dp)
//        )
//        Text(
//            text = koZnaZnaViewModel.questions[currentIndex].question,
//            style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center)
//        )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                koZnaZnaViewModel.questions[currentIndex].topic?.let {
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
                    text = koZnaZnaViewModel.questions[currentIndex].question,
                    style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center, color = Color.White)
                )
            }

            Spacer(Modifier.weight(1f))


            LazyColumn {
                items(koZnaZnaViewModel.questions[currentIndex].answers.size) { i ->
                    val answer = koZnaZnaViewModel.questions[currentIndex].answers[i] as Answer.OneAnswer
//                    key(answer.answer) {
                        GradientButton(
                            label = labels[i],
                            text = answer.answer,
                            textColor = TextColor,
                            gradient = when {
//                            answerSelected && answer.selected && answer.correct -> AppGradients.greenGradient
                               answer.selected -> AppGradients.greenGradient
                                else -> AppGradients.yellowGradient
                            },
                            onClick = {
                                koZnaZnaViewModel.setAnswer(answer)
                            },
                        )
//                    }
                    Spacer(Modifier.height(6.dp))
                }
            }

            Spacer(Modifier.height(12.dp))
            GradientButton(
                text = "Posalji odgovor",
                textColor = TextColor,
                gradient = AppGradients.yellowGradient,
                onClick = {
                    koZnaZnaViewModel.sendResult()
                },
            )
        }

}