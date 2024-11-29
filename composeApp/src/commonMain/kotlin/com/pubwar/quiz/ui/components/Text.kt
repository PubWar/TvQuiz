package com.pubwar.quiz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pubwar.quiz.ui.theme.SecondaryColor

@Composable
fun AutoSizeText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {

    val style = TextStyle(fontSize = textStyle.fontSize, fontFamily = textStyle.fontFamily, color= textStyle.color, fontWeight = textStyle.fontWeight)
    var scaledTextStyle by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }

    Box(
        modifier,
    )
    {

        Text(
            text,
            Modifier.drawWithContent {
                if (readyToDraw) {
                    drawContent()
                }
            },
            style = scaledTextStyle,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowHeight || textLayoutResult.didOverflowWidth) {
                    scaledTextStyle = scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.95)

                } else {
                    readyToDraw = true
                }

            }
        )
    }
}


@Composable
fun RoundedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.h1,
    bgColor: Color = SecondaryColor,
    borderColor: Color = Color.White
)
{
 return Box(
        modifier = modifier
            .shadow(elevation = 15.dp, spotColor = bgColor)
            .background(color = bgColor, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = borderColor)
            .padding(3.dp),
     Alignment.Center
    ) {
        Box(
            Modifier
                .padding(8.dp),

        ){
            AutoSizeText(
                text = text.uppercase(),
                textStyle = style.copy(color = Color.White),
            )
        }


    }
}