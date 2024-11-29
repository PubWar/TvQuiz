package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.LightBLue
import com.pubwar.quiz.ui.theme.TextColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.TypeAnswerViewModel
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*


@Composable
fun TypeAnswer(typeAnswerViewModel: TypeAnswerViewModel, userFinished: () -> Unit) {

    val currentIndex by typeAnswerViewModel.currentIndex.collectAsStateWithLifecycle()
    val typedAnswer by typeAnswerViewModel.typedAnswer.collectAsStateWithLifecycle()
    val gameIsFinished by typeAnswerViewModel.gameIsFinished.collectAsStateWithLifecycle()
    val answerIsSent by typeAnswerViewModel.answerIsSent.collectAsStateWithLifecycle()
    val answerIsCorrect by typeAnswerViewModel.answerIsCorrect.collectAsStateWithLifecycle()
    val time by typeAnswerViewModel.timeToNext.collectAsStateWithLifecycle()

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
            Text(
                text = "Oblast",
                style = MaterialTheme.typography.caption.copy(
                    textAlign = TextAlign.Center,
                    color = Yellow
                )
            )
            Text(
                text = "OPŠTA KULTURA",
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color = Yellow
                )
            )
            Spacer(Modifier.height(42.dp))
            Text(
                text = "Preostalo vreme",
                style = MaterialTheme.typography.caption.copy(
                    textAlign = TextAlign.Center,
                    color = LightBLue
                )
            )
            Text(
                text = time,
                style = MaterialTheme.typography.h1.copy(
                    textAlign = TextAlign.Center,
                    color = LightBLue
                )
            )
        }

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(
                    when {
                        answerIsSent && answerIsCorrect -> AppGradients.greenGradient
                        answerIsSent && !answerIsCorrect -> AppGradients.redGradient
                        else -> AppGradients.whiteGradient
                    },
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(2.dp)
                .height(100.dp)
,
            Alignment.Center

        ) {

            TextField(
                textStyle = MaterialTheme.typography.subtitle2.copy(
                    color =
                    when {
                        answerIsSent && !answerIsCorrect -> Color.White
                        else -> TextColor
                    },

                    textAlign = TextAlign.Center
                ),

                modifier = Modifier
                    .onPreviewKeyEvent {
                        if (it.key == Key.Enter) {
                            //do action
                            typeAnswerViewModel.checkAnswer()
                            true
                        } else {
                            false
                        }
                    },
                value = typedAnswer,
                enabled = !answerIsSent,
                maxLines = 1,
                singleLine = true,
                onValueChange = {
                    typeAnswerViewModel.setAnswer(it)
                },
                keyboardActions = KeyboardActions(onDone = {
                    typeAnswerViewModel.checkAnswer()
                }),

                placeholder = {
                    Text("Unesi odgovor", style = MaterialTheme.typography.subtitle2.copy(color = Color(0xFFD3D3D4), textAlign = TextAlign.Center)
                    ) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = TextColor,
                    backgroundColor = Color.Transparent, // Transparent background
                    cursorColor = TextColor, // Cursor color
                    focusedIndicatorColor = Color.Transparent, // No underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // No underline when unfocused
                    disabledIndicatorColor = Color.Transparent,
                ),
            )
        }

        AnimatedVisibility(
            visible = answerIsSent
        )
        {

           Column {
               Spacer(Modifier.height(50.dp))
               Image(
                   painter = painterResource(if(answerIsCorrect) Res.drawable.thumb_up else Res.drawable.thumb_down),
                   contentDescription = "thumb",
                   modifier = Modifier
                       .width(100.dp)
                       .height(100.dp)
               )

               Spacer(Modifier.height(20.dp))

               Text(
                   text = if (answerIsCorrect) "Vas odgovor je tacan!" else "Vas odgovor nije tacan!",
                   style = MaterialTheme.typography.subtitle1.copy(color = Yellow)
               )
           }







        }

        Spacer(Modifier.weight(1f))

    }
}