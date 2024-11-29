package com.pubwar.quiz.ui.screens.quiz.games


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.CyrillicKeyboard
import com.pubwar.quiz.ui.view_models.AsocijacijeViewModel
import com.pubwar.quiz.ui.view_models.ColumnState
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.send_button

@Composable
fun Asocijacije(viewModel : AsocijacijeViewModel) {
    var text by remember { mutableStateOf("") }
    var activeTextField by remember { mutableStateOf<Int?>(null) } // Track active text field
    var textFieldValues = remember { mutableStateOf(List(5) { "" }) } // Five text fields' content

    val columnState by viewModel.columnState.collectAsStateWithLifecycle()



    fun sendAnswer(index : Int, textFieldIndex: Int)
    {
        viewModel.setAnswer(index, textFieldValues.value[textFieldIndex])
        textFieldValues.value = textFieldValues.value.toMutableList().apply {
            this[textFieldIndex] = ""
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .weight(1F)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        )
        {
            Column() {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Frame 87
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        // Frame 11

                        ColumnComponent( columnState[0])
                        ColumnComponent( columnState[1])
                        ColumnComponent( columnState[2])
                        ColumnComponent(columnState[3])

                        CustomEditText(
                            text = textFieldValues.value[0], onClick = {
                                activeTextField = 0
                            },
                            isSelected = activeTextField == 0,
                            columnState[16],
                            {
                                sendAnswer(16, 0)
                            }
                        )
                    }
                    // Frame 88
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        // Frame 11
                        ColumnComponent(columnState[4])
                        ColumnComponent(columnState[5])
                        ColumnComponent(columnState[6])
                        ColumnComponent( columnState[7])
                        // Frame 15

                        CustomEditText(
                            text = textFieldValues.value[1], onClick = {
                                activeTextField = 1
                            },
                            isSelected = activeTextField == 1,
                                    columnState[17],
                            {
                                sendAnswer(17, 1)
                            }

                        )
                    }
                }

// Frame 79
                Spacer(modifier = Modifier.height(16.dp))

                CustomEditText(
                    text = textFieldValues.value[4], onClick = {
                        activeTextField = 4
                    },
                    isSelected = activeTextField == 4,
                    columnState[20],
                    {
                        sendAnswer(20, 4)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Frame 90
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Frame 87
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        // Frame 15
                        CustomEditText(
                            text = textFieldValues.value[2], onClick = {
                                activeTextField = 2
                            },
                            isSelected = activeTextField == 2,
                            columnState[18],
                            {
                                sendAnswer(18, 2)
                            }
                        )
                        ColumnComponent( columnState[8])
                        ColumnComponent( columnState[9])
                        ColumnComponent( columnState[10])
                        ColumnComponent( columnState[11])
                    }
                    // Frame 88
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        // Frame 15
                        CustomEditText(
                            text = textFieldValues.value[3], onClick = {
                                activeTextField = 3
                            },
                            isSelected = activeTextField == 3,
                            columnState[19],
                            {
                                sendAnswer(19, 3)
                            }
                        )
                        ColumnComponent( columnState[12])
                        ColumnComponent( columnState[13])
                        ColumnComponent( columnState[14])
                        ColumnComponent( columnState[15])
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        CyrillicKeyboard {
            activeTextField?.let { index ->
                textFieldValues.value = textFieldValues.value.toMutableList().apply {
                    this[index] += it
                }
            }
        }
    }


}

@Composable
fun ColumnComponent(state: ColumnState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff061b77), RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        // Б3
        Text(
            style =
            // RTS Sans / Bold
            TextStyle(
                fontWeight = FontWeight(700),
                fontSize = 16.sp,
            ),
            text = if (state.opened) {
                state.openState
            }   else {
                state.columnId
            },
            color = Color(0xffffffff),
        )
    }
}


@Composable
fun CustomEditText(text: String, onClick: () -> Unit, isSelected: Boolean, state: ColumnState, sendAnswer: () -> Unit) {
    Row(
        modifier = Modifier
            .height(38.dp)
            .fillMaxWidth()
            .background(if (isSelected) Color.LightGray else Color.White, RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        // Колона А
        Box(
            modifier =
            Modifier
                .weight(1f)
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        )
        {
            Text(
                style =
                // RTS Sans / Regular
                TextStyle(
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp,
                ),
                text = if (state.opened) {
                    state.openState
                }   else {
                    text
                },
                color = Color(0xff9fb3be),
            )
        }
        // key
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(38.dp)
                .background(
                    Color(0xffec0000),
                    RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                )
                .clickable {
                    sendAnswer()
                }
                .padding(10.dp),
        ) {
            // Frame
            Box(
                modifier = Modifier
                    .size(24.dp),
            ) {
                // ...
                Image(
                    painter = painterResource(Res.drawable.send_button),
                    contentDescription = "send",
                    modifier = Modifier
                        .fillMaxSize(1.6f)
                        .align(Alignment.Center),
                )
            }
        }
    }
}