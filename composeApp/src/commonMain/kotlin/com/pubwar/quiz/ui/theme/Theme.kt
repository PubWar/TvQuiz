package com.pubwar.quiz.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.background


val LightColorScheme = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    background = PrimaryColor, // Background color for the entire app
)

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = AppTypography(),
        shapes = AppShapes,
        // Make sure the content goes under the status bar
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)

            ) {
                Image(
                    painter = painterResource(Res.drawable.background),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    content()
                }
            }
        }
    )
}



@Composable
fun IntroTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = AppTypography(),
        shapes = AppShapes,
        // Make sure the content goes under the status bar
        content = {
            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(brush = linearGradient)
                        Modifier
                        .fillMaxSize()
                    .background(BackgroundColor)

            ) {
                Image(
                    painter = painterResource(Res.drawable.background),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    content()
                }
            }
        }
    )
}
