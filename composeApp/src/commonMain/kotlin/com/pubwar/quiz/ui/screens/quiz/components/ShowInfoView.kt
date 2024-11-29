package com.pubwar.quiz.ui.screens.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.pubwar.quiz.ui.theme.Yellow
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*

@Composable
fun ShowInfoView(showNumber : String, cycleNumber: String, showNumberInCycle: String)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.show_number, showNumber),
            style = MaterialTheme.typography.body2.copy(color = Yellow)
        )
        Text(
            text = stringResource(
                Res.string.cycle_number,
                showNumberInCycle,
                cycleNumber
            ),
            style = MaterialTheme.typography.body2.copy(color = Yellow)
        )
    }
}