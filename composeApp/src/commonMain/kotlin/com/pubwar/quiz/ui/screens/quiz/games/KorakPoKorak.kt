package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.SpeechToTextManager
import com.pubwar.quiz.ui.components.CyrillicKeyboard
import com.pubwar.quiz.ui.components.EditTextWithButton
import com.pubwar.quiz.ui.components.RedButton
import com.pubwar.quiz.ui.theme.ButtonColor
import com.pubwar.quiz.ui.theme.InfoColor
import com.pubwar.quiz.ui.view_models.KorakPoKorakViewModel
import com.pubwar.quiz.utills.toCyrilic
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*



@Composable
fun KorakPoKorak(
    korakPoKorakViewModel: KorakPoKorakViewModel,
    userFinished: (message: String) -> Unit
) {
    val input by korakPoKorakViewModel.input.collectAsStateWithLifecycle()
    val answerIsSend by korakPoKorakViewModel.answerIsSend.collectAsStateWithLifecycle()
    val answerIsCorrect by korakPoKorakViewModel.showInfo.collectAsStateWithLifecycle()
    val points by korakPoKorakViewModel.points.collectAsStateWithLifecycle()

    val speechToTextManager: SpeechToTextManager = koinInject<SpeechToTextManager>()
    speechToTextManager.setOnResultListener {
        println("Result STT: $it")
        korakPoKorakViewModel.inputChar(it.toCyrilic())
    }

    LaunchedEffect(answerIsSend) {
        if (answerIsSend)
            userFinished("")
    }

    LaunchedEffect(points) {
        if (points < 5)
            userFinished("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the text being typed

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.main_icon),
                contentDescription = "skocko",
                modifier = Modifier
                    .width(110.dp)
                    .height(165.dp)
                    .scale(0.6f)
            )

            AnimatedVisibility(
                visible = answerIsCorrect
            )
            {
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                )
                {

                    Text(
                        text = stringResource(Res.string.incorrect),
                        style = MaterialTheme.typography.body2.copy(
                            color = ButtonColor,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }


        }
        Box(Modifier.padding(8.dp))

        {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditTextWithButton(
                    input.uppercase(), onButtonClick = {
                        korakPoKorakViewModel.removeChar()
                    })

                Spacer(Modifier.height(6.dp))
                Text(
                    stringResource(Res.string.try_for_points, points),
                    style = MaterialTheme.typography.body2.copy(
                        color = InfoColor
                    )
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))
        // Use the custom keyboard
        CyrillicKeyboard(onKeyPress = { key ->
            when (key) {
                "SPACE" -> korakPoKorakViewModel.inputChar(" ")
                "MIC" -> {
                    korakPoKorakViewModel.clearAnswer()
                    speechToTextManager.startListening()
                }
                else -> korakPoKorakViewModel.inputChar(key)  // Add the pressed key to the input
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Box(Modifier.padding(8.dp))
        {
            RedButton(

                enabled = !korakPoKorakViewModel.answerIsSend.value,
                text = stringResource(Res.string.send_result_button),
                onClick = {
                    korakPoKorakViewModel.checkAnswer()
                },
            )
        }

    }
}
