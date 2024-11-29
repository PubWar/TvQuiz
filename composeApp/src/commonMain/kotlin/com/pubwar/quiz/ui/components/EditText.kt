package com.pubwar.quiz.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.PrimaryColor
import com.pubwar.quiz.ui.theme.SecondaryColor
import com.pubwar.quiz.ui.theme.TextColor
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.icon_erase


@Composable
fun EditTextWithButton(
    text: String,
    onButtonClick: () -> Unit,
    buttonIcon: Painter = painterResource(Res.drawable.icon_erase)
) {
    Box(
        modifier = Modifier
            .border(width = 0.5.dp, color = PrimaryColor, shape = MaterialTheme.shapes.small)
            .fillMaxWidth()
            .height(48.dp)
            .clip(MaterialTheme.shapes.small) // Apply rounded corners
            .background(Color.White)
            .padding(5.dp),
        Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        )
        {

            AutoSizeText(
                text.uppercase(),
                MaterialTheme.typography.body1.copy(letterSpacing = 4.sp, color = SecondaryColor, textAlign = TextAlign.Start),
                modifier = Modifier
                    .weight(Float.MAX_VALUE),
                )


            IconButton(onClick = onButtonClick,) {
                Image(
                    modifier = Modifier.background(Color.Blue),
                    painter = buttonIcon,
                    contentDescription = "Image Button",
                )
            }
        }
    }
}


@Composable
fun EditText(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(size = 8.dp))
        .background(
            AppGradients.whiteGradient,
        )
        .border(
            width = 2.dp,
            color = Color(0xFFFFFFFF),
            shape = RoundedCornerShape(size = 8.dp)
        )
        .padding(2.dp),
    value: String,
    onValueChange: (String) -> Unit,
    enable: Boolean = true,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
)
{

    Box(
        modifier,
        Alignment.Center

    ) {
        TextField(
            textStyle = MaterialTheme.typography.subtitle2.copy(
                color = TextColor,
                textAlign = TextAlign.Center
            ),

            value = value,
            enabled = enable,
            maxLines = 1,
            singleLine = true,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color(0xFFD3D3D4),
                        textAlign = TextAlign.Center
                    )
                )
            },
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
}