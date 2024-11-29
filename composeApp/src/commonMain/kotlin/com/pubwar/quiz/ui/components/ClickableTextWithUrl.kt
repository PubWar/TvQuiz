package com.pubwar.quiz.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun ClickableTextWithUrl(
    text: String,
    urlText: String,
    url: String
) {
    val uriHandler = LocalUriHandler.current

//    fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center)
    // Create the text with a URL
    val annotatedString = buildAnnotatedString {

        withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp)) {
            append(text)
        }
        pushStringAnnotation(tag = "URL", annotation = url)

        withStyle(style = SpanStyle(color = Color.Yellow, fontSize = 16.sp)) {
            append(urlText)
        }

        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}