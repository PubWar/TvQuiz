package com.pubwar.quiz.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pubwar.quiz.ui.components.ClickableTextWithUrl
import com.pubwar.quiz.ui.components.RedButton
import com.pubwar.quiz.ui.theme.Blue
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.hello
import pubwartvquiz.composeapp.generated.resources.intro_info
import pubwartvquiz.composeapp.generated.resources.intro_subtitle1
import pubwartvquiz.composeapp.generated.resources.intro_subtitle2
import pubwartvquiz.composeapp.generated.resources.main_icon
import pubwartvquiz.composeapp.generated.resources.open_qr_scanner


@Composable
fun IntroScreen(navController: NavHostController)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(3F))
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(stringResource(Res.string.hello, "Ана"), style =  MaterialTheme.typography.subtitle1.copy(color = Blue, textAlign = TextAlign.Center))
                Spacer(Modifier.height(12.dp))
                Text(stringResource(Res.string.intro_subtitle1), style = MaterialTheme.typography.body1.copy(color = Blue, textAlign = TextAlign.Center))
                Spacer(Modifier.height(12.dp))
                Text(stringResource(Res.string.intro_subtitle2), style = MaterialTheme.typography.body1.copy(color = Blue, textAlign = TextAlign.Center))
            }

        }

        Spacer(Modifier.height(12.dp))
        Image(
            painter = painterResource(Res.drawable.main_icon),
            contentDescription = "skocko",
            modifier = Modifier
                .width(179.dp)
                .height(196.dp)
        )

        Spacer(Modifier.weight(2F))
        RedButton(
            enabled = true,
            text = stringResource(Res.string.open_qr_scanner),
            onClick = {
                navController.navigate("/qrscanner")
            },
        )

        Spacer(Modifier.weight(1F))
        ClickableTextWithUrl(stringResource(Res.string.intro_info),"www.rts.rs/slagalica", "https://www.rts.rs/lat/tv/rts1/2131252/slagalica.html")
    }
}