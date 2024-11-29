package com.pubwar.quiz.ui.screens.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.domain.model.ViewType
import com.pubwar.quiz.ui.screens.quiz.components.GameContent
import com.pubwar.quiz.ui.screens.quiz.components.ShowInfoView
import com.pubwar.quiz.ui.screens.quiz.components.TotalPointsView
import com.pubwar.quiz.ui.theme.ButtonColor
import com.pubwar.quiz.ui.theme.Yellow
import com.pubwar.quiz.ui.view_models.QuizViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*


@Composable
fun Quiz(quizViewModel: QuizViewModel) {

    val state by quizViewModel.state.collectAsStateWithLifecycle()
    val ranking by quizViewModel.ranking.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.gameType != ViewType.KRAJ)
                TotalPointsView(state.totalPoints)

            AnimatedVisibility(
                visible = state.gameType in listOf(ViewType.REKLAME, ViewType.UVOD)
            ) {

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

                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(15.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = state.pauseMessage,
                            style = MaterialTheme.typography.body2.copy(
                                color = ButtonColor,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }

            //UVOD
            AnimatedVisibility(
                visible = state.gameType in listOf(ViewType.REKLAME)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.number_of_points),
                        style = MaterialTheme.typography.body2.copy(color = Yellow)
                    )
                    Text(
                        text = state.pointsOfTheLastGame.toString(),
                        style = MaterialTheme.typography.h2.copy(color = Yellow)
                    )


                    if (ranking != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(Res.string.ranking),
                            style = MaterialTheme.typography.body1.copy(color = Yellow)
                        )
                        Text(
                            text = ranking!!,
                            style = MaterialTheme.typography.body2.copy(color = Yellow)
                        )
                    }
                }
            }

            //UVOD
            AnimatedVisibility(
                visible = state.gameType == ViewType.UVOD
            )
            {
                ShowInfoView(state.showNumber, state.showCycleNumber, state.showNumberInCycle)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        GameContent(viewType = state.gameType, game = state.currentGame, state.games) {
            quizViewModel.gamerFinishTheGame()
        }
    }
}


