package com.pubwar.quiz.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppGradients {
    val linearGradient = Brush.verticalGradient(
        colors = listOf(Color.White, Color.White, Gray),
    )

    val radialGradient = Brush.radialGradient(
        colors = listOf(Color(0xFF03DAC6), Color(0xFF6200EE)),
        center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
        radius = 500f
    )

    val sweepGradient = Brush.sweepGradient(
        colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC6), Color(0xFF6200EE))
    )

    val lightBlueGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF13BDCE), Color(0xFF13BDCE), Color(0xFF20768C)),
    )

    val yellowGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFE39226), Color(0xFFFEDD14), Color(0xFFE39226)),
    )

    val greenGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFA5CD39), Color(0xFFC3FE14), Color(0xFFA5CD39)),
    )

    val redGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFBA1D1D), Color(0xFFFC6464), Color(0xFFBA1D1D)),
    )

    val whiteGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF), Color(0xFFFFFFFF)),
    )
}
