package com.pubwar.quiz.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pubwar.quiz.isDigit
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.theme.Blue
import com.pubwar.quiz.ui.theme.CircleBlue
import com.pubwar.quiz.ui.theme.EditTextColor
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
                MaterialTheme.typography.body1.copy(
                    letterSpacing = 4.sp,
                    color = SecondaryColor,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .weight(Float.MAX_VALUE),
            )


            IconButton(onClick = onButtonClick) {
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
    keyboardType: KeyboardType = KeyboardType.Text

) {

    Box(
        modifier,
        Alignment.CenterStart

    ) {
        TextField(
            textStyle = MaterialTheme.typography.subtitle2.copy(
                color = EditTextColor,
                textAlign = TextAlign.Start
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
                        textAlign = TextAlign.Start
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = EditTextColor,
                backgroundColor = Color.Transparent, // Transparent background
                cursorColor = EditTextColor, // Cursor color
                focusedIndicatorColor = Color.Transparent, // No underline when focused
                unfocusedIndicatorColor = Color.Transparent, // No underline when unfocused
                disabledIndicatorColor = Color.Transparent,
            ),

            keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        )
    }
}

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Phone Number"
) {
    var isError by remember { mutableStateOf(false) }
    val phoneNumberRegex = Regex("^\\+?[0-9]*\$") // Allows optional "+" and digits only

    EditText(
        value = phoneNumber,
        onValueChange = { input ->
            if (phoneNumberRegex.matches(input)) {
                isError = false
                onPhoneNumberChange(input)
            } else {
                isError = true
            }
        },
        placeholder = placeholder,
        keyboardType = KeyboardType.Phone
    )

    if (isError) {
        Text(
            text = "Invalid phone number",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    modifier: Modifier = Modifier,
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Box(
        modifier = modifier
        .clip(shape = RoundedCornerShape(size = 8.dp))
        .background(
            AppGradients.whiteGradient,
        )
        .border(
            width = 2.dp,
            color = if(isFocused) Blue else Color(0xFFFFFFFF),
            shape = RoundedCornerShape(size = 8.dp)
        )
        .padding(2.dp),
        Alignment.Center
    )
    {
        Text(
            text = char,
            style = MaterialTheme.typography.h4,
            color = EditTextColor,
            textAlign = TextAlign.Center
        )
    }

}




