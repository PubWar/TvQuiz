package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.domain.model.Answer
import com.pubwar.quiz.ui.components.GradientButton
import com.pubwar.quiz.ui.components.OutlinedButtonComponent
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.Blue
import com.pubwar.quiz.ui.theme.LightBLue
import com.pubwar.quiz.ui.theme.SecondaryColor
import com.pubwar.quiz.ui.theme.TextColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.KoZnaZnaViewModel
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.main_icon

@Composable
fun KoZnaZna(koZnaZnaViewModel: KoZnaZnaViewModel, userFinished: () -> Unit) {

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
        Image(
            painter = painterResource(Res.drawable.main_icon),
            contentDescription = "skocko",
            modifier = Modifier
                .width(110.dp)
                .height(165.dp)
        )
        Text(
            text = koZnaZnaViewModel.questions[currentIndex].question,
            style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center)
        )

        Spacer(Modifier.weight(1f))
        LazyColumn {
            items(koZnaZnaViewModel.questions[currentIndex].answers.size) { i ->
                val answer = koZnaZnaViewModel.questions[currentIndex].answers[i] as Answer.OneAnswer
                key(answer.answer) {

                    OutlinedButtonComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp),
                        text = answer.answer,
                        enabled = true,
                        onClick = {
                            koZnaZnaViewModel.setAnswer(answer)
                        },
                        style = MaterialTheme.typography.subtitle1,
                        bgColor = when {
                            answerSelected && answer.selected && answer.correct -> Blue
                            answerSelected && answer.selected -> Color.Red
                            else -> SecondaryColor
                        },
                    )
                }
                Spacer(Modifier.height(6.dp))
            }
        }


    }
}