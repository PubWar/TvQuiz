package com.pubwar.quiz.ui.screens.quiz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.ui.components.AutoSizeText
import com.pubwar.quiz.ui.components.OutlinedButtonComponent
import com.pubwar.quiz.ui.theme.Blue
import com.pubwar.quiz.ui.theme.Yellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*


fun calculateTotal(games: List<Game>?): Int {

    var total = 0
    if (games != null) {
        for(game in games) {
            total += game.points
        }
    }

    println("Total result: $total")
    return total
}

@Composable
@Preview
fun ResultView(games: List<Game>?) {

    val images = arrayOf(
        Res.drawable.slagalica_banner,
        Res.drawable.moj_broj_banner,
        Res.drawable.korak_po_korak_banner,
        Res.drawable.skocko_banner,
        Res.drawable.spojnice_banner,
        Res.drawable.ko_zna_zna_banner,
        Res.drawable.asocijacije_banner
    )


    println(games?.size)
    val total = remember {
        calculateTotal(games)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row() {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            )
            {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(Res.string.congrats, "Ана"),
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Blue,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        stringResource(Res.string.congrats_info, "120.008", 109),
                        style = MaterialTheme.typography.body1.copy(
                            color = Blue,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }


        Row(
            Modifier.weight(2F),
            verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(Res.drawable.main_icon),
                contentDescription = "skocko",
                modifier = Modifier,
            )
        }


        for (i in 0..<images.size) {
            Row(
                Modifier.weight(1F).padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
            ) {
                if (games != null) {
                    OutlinedButtonComponent(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxHeight(),
                        text = if(games.getOrNull(i*2) != null){ games[i*2].points.toString()}  else { "0"},
                        enabled = true,
                        onClick = {

                        },
                        style = MaterialTheme.typography.subtitle1.copy(color = Yellow)
                    )
                }


                Image(
                    painter = painterResource(images[i]),
                    contentDescription = "banner_$i",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1.23F),
                )

                if (games != null) {
                    OutlinedButtonComponent(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxHeight(),
                        text = if(games.getOrNull(((i+1)*2)-1) != null){ games?.get(((i+1)*2)-1)!!.points.toString()}  else {"0"},
                        enabled = true,
                        onClick = {

                        },
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }


        Row(
            Modifier.weight(1F).padding(top = 12.dp, start = 12.dp, end = 12.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {

                AutoSizeText(
                    text = total.toString(),
                    textStyle = MaterialTheme.typography.h2.copy(color = Color.White),
                )
            }

        }

        Spacer(Modifier.weight(0.5F))
    }

}


@Composable
fun ResultItem() {
    Box(
        Modifier
            .width(278.dp)
            .padding(4.dp),

        )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(4.dp),
                )
                .border(2.dp, Color.White, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "0",
                style = MaterialTheme.typography.h2.copy(color = Color.White),

                )
        }
    }
}