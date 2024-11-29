package com.pubwar.quiz.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.rts_sans_bold
import pubwartvquiz.composeapp.generated.resources.rts_sans_italic
import pubwartvquiz.composeapp.generated.resources.rts_sans_regular


@Composable
fun RTSFontFamily() = FontFamily(
    Font(Res.font.rts_sans_regular, weight = FontWeight.Normal),
    Font(Res.font.rts_sans_bold, weight = FontWeight.Bold),
    Font(Res.font.rts_sans_italic, weight = FontWeight.Normal),
)

@Composable
fun AppTypography() = Typography().run {

    val fontFamily = RTSFontFamily()
    val perc = 0.95
//48, regular = h1
//36, bold = h2
//36, regular = h3
//24, regular = button
//24, medium = subtitle2
//24, bold = subtitle1
//20, bold = body2
//20, regular = body1
//16, regular = caption

    copy(
        h1 = h1.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (48*perc).sp,
            lineHeight = (48 * perc).sp,
            color = TextColor
        ),

        h2 = h2.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (36*perc).sp,
            color = TextColor
        ),

        h3 = h3.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (36*perc).sp,
            color = TextColor,
        ),

        subtitle1 = subtitle1.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (24*perc).sp,
            color = TextColor
        ),

        subtitle2 = subtitle2.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (24*perc).sp,
            color = TextColor
        ),

        body1 = body1.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (20*perc).sp,
            color = TextColor
        ),

        body2 = body2.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (20*perc).sp,
            color = TextColor
        ),

        button = button.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (24*perc).sp,
        ),


        caption = caption.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (16*perc).sp
        )
    )
}



