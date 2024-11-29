package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.AutoSizeText
import com.pubwar.quiz.ui.components.EditTextWithButton
import com.pubwar.quiz.ui.components.OutlinedButtonComponent
import com.pubwar.quiz.ui.components.RedButton
import com.pubwar.quiz.ui.theme.ButtonColor
import com.pubwar.quiz.ui.view_models.MojBrojViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*


@Composable
fun MojBroj(mojBrojViewModel: MojBrojViewModel, userFinished: () -> Unit) {

    val answer by mojBrojViewModel.answer.collectAsStateWithLifecycle()
    val result by mojBrojViewModel.result.collectAsStateWithLifecycle()
    val correct by mojBrojViewModel.correct.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            Modifier.weight(2F)
        )
        {
            Image(
                painter = painterResource(Res.drawable.main_icon),
                contentDescription = "skocko",
                modifier = Modifier
                    .width(110.dp)
                    .height(165.dp)
            )
        }

        Row(
            Modifier.padding(top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditTextWithButton(answer.uppercase(), onButtonClick = { mojBrojViewModel.backspace() })
                Spacer(Modifier.height(4.dp))
            }
        }

        Row(
            Modifier.padding(top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Number of columns in the grid
                verticalArrangement = Arrangement.spacedBy(0.dp), // Space between rows
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between columns
                modifier = Modifier.fillMaxWidth()
            ) {

                    item {

                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(stringResource(Res.string.correct_result))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, color = Color.White, shape = MaterialTheme.shapes.small)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(ButtonColor)
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            )
                            {

                                AutoSizeText(mojBrojViewModel.numbers.last().value, textStyle = MaterialTheme.typography.h2)

                            }
                        }

                    }


                item {

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(stringResource(Res.string.your_result))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, color = Color.White, shape = MaterialTheme.shapes.small)
                                .clip(MaterialTheme.shapes.small)
                                .background(Color.White)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        )
                        {
                            AutoSizeText(result.toString(), textStyle = MaterialTheme.typography.h2.copy(color = Color.Black))
                        }
                    }
                }

            }
        }


        Row (Modifier.padding(top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically){
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // Number of columns in the grid
                verticalArrangement = Arrangement.spacedBy(12.dp), // Space between rows
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between columns
                modifier = Modifier.fillMaxWidth()
            ) {

                mojBrojViewModel.numbers.take(4).forEach {

                    item {

                        OutlinedButtonComponent(
                            modifier = Modifier
                                .weight(1f),
                            text = it.value,
                            enabled = !it.isUsed,
                            onClick = {
                                mojBrojViewModel.setAnswer(it)
                            })
                    }
                }
            }
        }
        Row(
            Modifier.padding(top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Number of columns in the grid
                verticalArrangement = Arrangement.spacedBy(0.dp), // Space between rows
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between columns
                modifier = Modifier.fillMaxWidth()
            ) {

                mojBrojViewModel.numbers.takeLast(3).take(2).forEach {
                    item {

                        OutlinedButtonComponent(
                            modifier = Modifier
                                .fillMaxHeight(),
                            text = it.value,
                            enabled = !it.isUsed,
                            onClick = {
                                mojBrojViewModel.setAnswer(it)
                            })
                    }
                }
            }
        }

        Row(
            Modifier
                .padding(top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(6), // Number of columns in the grid
                verticalArrangement = Arrangement.spacedBy(12.dp), // Space between rows
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between columns
                modifier = Modifier.fillMaxWidth()
            ) {

                mojBrojViewModel.operations.forEach {
                    item {
                        OutlinedButtonComponent(
                            modifier = Modifier.aspectRatio(1f),
                            text = it.value,
                            enabled = true,
                            onClick = {
                                mojBrojViewModel.setAnswer(it)
                            })
                    }

                }
            }
        }
        Spacer(Modifier.weight(1F))
        Row(
            modifier = Modifier
                .height(85.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Add content here

                RedButton(
                    enabled = !mojBrojViewModel.answerIsSend.value,
                    text = stringResource(Res.string.send_result_button),
                    onClick = {
                        mojBrojViewModel.sendAnswer()
                        userFinished()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


//        Row (Modifier.background(color = Color.Magenta), verticalAlignment = Alignment.CenterVertically){
//            RedButton(
//                enabled = !mojBrojViewModel.answerIsSend.value,
//                text = stringResource(Res.string.send_result_button),
//                onClick = {
//                    mojBrojViewModel.sendAnswer()
//
//                },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }



    }
}

