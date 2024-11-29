package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pubwar.quiz.VideoPlayer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun NonGameView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        VideoPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
//            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            path = "test"
        )
//        Image(
//            painter = painterResource(Res.drawable.jelen_pivo),
//            contentDescription = "reklama",
//            modifier = Modifier
//                .fillMaxSize(), // Negate the padding,
//            contentScale = ContentScale.Crop
//        )
    }
}