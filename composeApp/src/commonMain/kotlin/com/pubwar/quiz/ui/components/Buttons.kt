package com.pubwar.quiz.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pubwar.quiz.ui.theme.AppGradients.lightBlueGradient
import com.pubwar.quiz.ui.theme.ButtonColor
import com.pubwar.quiz.ui.theme.SecondaryColor

@Composable
fun RedButton(
    enabled: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = ButtonColor,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .alpha((if (enabled) 1.0 else 0.6).toFloat())
            .shadow(elevation = 15.dp, spotColor = SecondaryColor, ambientColor = SecondaryColor)
            .height(48.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}

@Composable
fun OutlinedButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    style: TextStyle = MaterialTheme.typography.h1,
    bgColor: Color = SecondaryColor,
    borderColor: Color = Color.White
) {

    OutlinedButton(
        modifier = modifier
            .shadow(elevation = 15.dp, spotColor = bgColor)
            .alpha((if (enabled) 1.0 else 0.6).toFloat()),
        enabled = enabled,
        onClick = onClick,

        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = bgColor,
            disabledContentColor = Color.Gray,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(2.dp, borderColor),
        contentPadding = PaddingValues(3.dp),
    ) {
        Box(
            Modifier
                .padding(8.dp),


            ) {
            AutoSizeText(
                text = text.uppercase(),
                textStyle = style,
            )
        }
    }

}


@Composable
fun LightBlueButton(
    enabled: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),


        ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .background(lightBlueGradient)
                .fillMaxWidth()
                .height(65.dp)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                .shadow(
                    elevation = 20.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 8.dp)
                )
        )
        {
            Text(text = text, style = MaterialTheme.typography.button)
        }
    }
}


@Composable
fun GradientButton(
    label: String? = null,
    text: String,
    textColor: Color? = null,
    gradient: Brush,
    onClick: () -> Unit = { },

    ) {

    val displayTextColor = textColor ?: Color.White

    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .border(
                    width = 2.dp,
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(2.dp)
                .height(65.dp)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)

        ) {

            if (label != null) {
                Row (
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically

                    ){
                    Text(
                        text = label,
                        style = MaterialTheme.typography.h1.copy(color = displayTextColor)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.button.copy(color = displayTextColor),
                    )
                }
            } else
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    style = MaterialTheme.typography.button.copy(color = displayTextColor)
                )
        }
    }
}



