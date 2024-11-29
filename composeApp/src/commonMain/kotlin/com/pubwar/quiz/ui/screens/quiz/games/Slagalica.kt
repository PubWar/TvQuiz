package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.EditTextWithButton
import com.pubwar.quiz.ui.components.OutlinedButtonComponent
import com.pubwar.quiz.ui.components.RedButton
import com.pubwar.quiz.ui.view_models.SlagalicaViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*

@Composable
fun Slagalica(slagalicaViewModel: SlagalicaViewModel, userFinished: () -> Unit) {

    val answer by slagalicaViewModel.answer.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(Res.drawable.main_icon),
            contentDescription = "skocko",
            modifier = Modifier
                .width(110.dp)
                .height(165.dp)

        )

        EditTextWithButton(answer.uppercase(), onButtonClick = {slagalicaViewModel.backspace()})

        Spacer(modifier = Modifier.weight(1f))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // Number of columns in the grid
            verticalArrangement = Arrangement.spacedBy(12.dp), // Space between rows
            horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between columns
            modifier = Modifier.fillMaxWidth()
        ) {
            items(slagalicaViewModel.letters.size) { index ->
                val it = slagalicaViewModel.letters[index]
                OutlinedButtonComponent(
                    modifier = Modifier
                        .aspectRatio(1f),
                    text = it.letter,
                    enabled = !it.isUsed,
                    onClick = {
                        slagalicaViewModel.setAnswer(it)
                    })
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        RedButton(
            enabled = !slagalicaViewModel.answerIsSend.value,
            text = stringResource(Res.string.send_result_button),
            onClick = {
                 slagalicaViewModel.sendAnswer()
                userFinished()
            },
        )
    }
}
