package com.pubwar.quiz.ui.screens.quiz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.profile_image

@Composable
fun TotalPointsView(totalPoints : Int)
{
    Row(
        Modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier
                .background(Color.White, shape = RoundedCornerShape(18.dp)),
            verticalAlignment = Alignment.CenterVertically

        )
        {
            Image(
                painter = painterResource(Res.drawable.profile_image),
                contentDescription = "profile",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
            )

            Text(
                totalPoints.toString(),
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.subtitle2.copy(color = Color.Black)
            )
        }

        Spacer(Modifier.weight(1F))
    }
}
