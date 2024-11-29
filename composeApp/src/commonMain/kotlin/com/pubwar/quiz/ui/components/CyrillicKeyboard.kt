package com.pubwar.quiz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pubwar.quiz.ui.theme.SecondaryColor
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.mic

@Composable
fun CyrillicKeyboard(onKeyPress: (String) -> Unit) {

    var parentSize by remember { mutableStateOf(Size.Zero) }
    val parentWidth = parentSize.width
    // Number of keys in the row
    val keyCount = 11
    // Define the size of each key based on the total available width and the number of keys
    val keyWidth = if (parentWidth > 0) parentWidth / keyCount else 0f

    val rows = listOf(
        "ЉЊЕРТЗУИОПШ",
        "АСДФГХЈКЛЧЋ",
        "ЏЦВБНМЂЖ"
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryColor)
            .onGloballyPositioned { coordinates ->
                // Get the size of the parent
                parentSize = coordinates.size.toSize()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in rows) {
            KeyboardRow(letters = row, keyWidth = keyWidth, onKeyPress)
        }
    }


    Spacer(Modifier.height(3.dp))
    // Add space bar and delete key row
    Row(

    ) {
        Button(
            modifier = Modifier // Adjust the size of the keys
                .padding(1.dp)
                .fillMaxWidth(0.5F)
                .height(with(LocalDensity.current) {
                    (keyWidth * 1.4F).toDp()
                })
                .background(SecondaryColor, shape = MaterialTheme.shapes.small),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent, // Set the background color
                contentColor = Color.White // Set the content (text) color
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onKeyPress("SPACE")
            }
        ) {
            AutoSizeText(
//                text = stringResource(Res.string.space),
                text = " ",
                textStyle = MaterialTheme.typography.body2,
            )
        }


        Button(
            modifier = Modifier // Adjust the size of the keys
                .padding(1.dp)
                .height(with(LocalDensity.current) {
                    (keyWidth * 1.4F).toDp()
                })
                .background(SecondaryColor, shape = MaterialTheme.shapes.small),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent, // Set the background color
                contentColor = Color.White // Set the content (text) color
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                onKeyPress("MIC")
            }
        ) {

            Image(
                painter = painterResource(Res.drawable.mic),
                contentDescription = "mic",
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)

            )
        }

//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier // Adjust the size of the keys
//                    .padding(1.dp)
//                    .fillMaxWidth(0.5F)
//                    .height(with(LocalDensity.current) {
//                        (keyWidth * 1.3F).toDp()
//                    })
//                    .background(SecondaryColor, shape = MaterialTheme.shapes.small)
//                    .clickable { onKeyPress("SPACE") }
//            ){
//                Text(
//                    text = "РАЗМАК",
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Center,
//                    color = Color.White
//                )
//            }
//            CyrillicKey("SPACE", onKeyPress)
    }
}


@Composable
fun KeyboardRow(letters: String, keyWidth: Float, onKeyPress: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        letters.forEach { letter ->
            CyrillicKey(letter.toString(), onKeyPress, keyWidth)
        }
    }
}


@Composable
fun CyrillicKey(label: String, onKeyClick: (String) -> Unit, keyWidth: Float) {
    Button(
        modifier = Modifier
            .width(with(LocalDensity.current) { keyWidth.toDp() })
            .height(with(LocalDensity.current) {
                (keyWidth * 1.4F).toDp()
            }),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent, // Set the background color
            contentColor = Color.White // Set the content (text) color
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = {
            onKeyClick(label)
        }
    ) {
        AutoSizeText(
            text = label,
            textStyle = MaterialTheme.typography.body2,
        )
    }
}