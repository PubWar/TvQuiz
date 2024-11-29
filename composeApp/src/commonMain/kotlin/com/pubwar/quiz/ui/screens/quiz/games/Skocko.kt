package com.pubwar.quiz.ui.screens.quiz.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.RedButton
import com.pubwar.quiz.ui.theme.Blue
import com.pubwar.quiz.ui.view_models.SkockoViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.*

enum class SkockoEnum {
    SKOCKO, TREF, PIK, HERC, KARO, ZVEZDA
}

@Preview
@Composable
fun Skocko(viewModel: SkockoViewModel, userFinished: (message: String) -> Unit) {

    val currentIndex by viewModel.currentCombinationIndex.collectAsStateWithLifecycle()

    val combinations = arrayOf(
        viewModel.combinations[0].collectAsStateWithLifecycle(),
        viewModel.combinations[1].collectAsStateWithLifecycle(),
        viewModel.combinations[2].collectAsStateWithLifecycle(),
        viewModel.combinations[3].collectAsStateWithLifecycle(),
        viewModel.combinations[4].collectAsStateWithLifecycle(),
        viewModel.combinations[5].collectAsStateWithLifecycle()
    )

    val results = arrayOf(
        viewModel.results[0].collectAsStateWithLifecycle(),
        viewModel.results[1].collectAsStateWithLifecycle(),
        viewModel.results[2].collectAsStateWithLifecycle(),
        viewModel.results[3].collectAsStateWithLifecycle(),
        viewModel.results[4].collectAsStateWithLifecycle(),
        viewModel.results[5].collectAsStateWithLifecycle()
    )


    Column {
        Row(
            modifier = Modifier
                .weight(1.5f)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Add content here
                Image(
                    painter = painterResource(Res.drawable.main_icon),
                    contentDescription = "skocko",
                    modifier = Modifier
                        .width(110.dp)
                        .height(165.dp)
                        .defaultMinSize(minWidth = 55.dp, minHeight = 83.dp)

                )

            }
        }

        for (i in 1..6) {
            val combination = combinations[i - 1]
            val result = results[i - 1]
//            val color = if(currentIndex == i-1) Blue else Blue.copy(alpha = 0.5f)
            val color =  Blue
            val borderWidth =  if(currentIndex == i-1) 2.5.dp else 1.dp
             Row(
                 modifier = Modifier.weight(1f)
                     .padding(start = 8.dp, end = 8.dp),
                 horizontalArrangement = Arrangement.SpaceEvenly,
                 verticalAlignment = Alignment.CenterVertically,
             ) {

                 CombinationItem(
                     modifier = Modifier.weight(1f),
                     image = combination.value.elementAtOrNull(0),
                     backgroundColor = color,
                     onClick = {
                         viewModel.removeFromCombination(0)
                     },
                     borderWidth = borderWidth
                     )
                 CombinationItem(
                     modifier = Modifier.weight(1f),
                     image = combination.value.elementAtOrNull(1),
                     backgroundColor = color,
                     onClick = {
                         viewModel.removeFromCombination(1)
                     },
                     borderWidth = borderWidth)
                 CombinationItem(
                     modifier = Modifier.weight(1f),
                     image = combination.value.elementAtOrNull(2),
                     backgroundColor = color,
                     onClick = {
                         viewModel.removeFromCombination(2)
                     },
                     borderWidth = borderWidth)
                 CombinationItem(
                     modifier = Modifier.weight(1f),
                     image = combination.value.elementAtOrNull(3),
                     backgroundColor = color,
                     onClick = {
                         viewModel.removeFromCombination(3)
                     },
                     borderWidth = borderWidth)

                 ResultItem(modifier = Modifier.weight(0.5f), color = result.value[0])
                 ResultItem(modifier = Modifier.weight(0.5f), color = result.value[1])
                 ResultItem(modifier = Modifier.weight(0.5f), color = result.value[2])
                 ResultItem(modifier = Modifier.weight(0.5f), color = result.value[3])
             }

        }


        Row(
            modifier = Modifier
                .height(85.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Add content here

                LazyVerticalGrid(
                    columns = GridCells.Fixed(6), // Number of columns in the grid
                    verticalArrangement = Arrangement.spacedBy(2.dp), // Space between rows
                    horizontalArrangement = Arrangement.spacedBy(2.dp), // Space between columns
                    modifier = Modifier.fillMaxWidth(1f)
                ) {

                    item { ->
                        CombinationItem(Modifier, SkockoEnum.SKOCKO, onClick = {
                            viewModel.addToCombination(SkockoEnum.SKOCKO)
                        })
                    }
                    item { ->
                        CombinationItem(
                            Modifier, SkockoEnum.TREF,
                            onClick = {
                                viewModel.addToCombination(SkockoEnum.TREF)
                            })
                    }
                    item { ->
                        CombinationItem(
                            Modifier, SkockoEnum.PIK,
                            onClick = {
                                viewModel.addToCombination(SkockoEnum.PIK)
                            })
                    }
                    item { ->
                        CombinationItem(
                            Modifier, SkockoEnum.HERC,
                            onClick = {
                                viewModel.addToCombination(SkockoEnum.HERC)
                            })
                    }
                    item { ->
                        CombinationItem(
                            Modifier, SkockoEnum.KARO,
                            onClick = {
                                viewModel.addToCombination(SkockoEnum.KARO)
                            })
                    }
                    item { ->
                        CombinationItem(
                            Modifier, SkockoEnum.ZVEZDA,
                            onClick = {
                                viewModel.addToCombination(SkockoEnum.ZVEZDA)
                            })
                    }
                }


            }
        }

        Row(
            modifier = Modifier
                .height(85.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Add content here

                RedButton(
                    enabled = true,
                    text = stringResource(Res.string.send_result_button),
                    onClick = {
                        if (viewModel.checkAnswer()) {
                            userFinished("");
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CombinationItem(
    modifier: Modifier,
    image: SkockoEnum? = null,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
    borderWidth: Dp = 2.dp
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(3.dp)
            .clickable { onClick() },

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = borderWidth,
                    color = Color(0xFFCDE1F1),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .background(color = backgroundColor, shape = RoundedCornerShape(size = 8.dp))


        ) {

            if (image != null)
                Image(
                    painter = painterResource(getSymbolImage(image)),
                    contentDescription = "skocko",
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                        .align(Alignment.Center),
                )


        }
    }
}

fun getSymbolImage(symbol: SkockoEnum): DrawableResource {
    return when (symbol) {
        SkockoEnum.SKOCKO -> Res.drawable.skocko_mali;
        SkockoEnum.TREF -> Res.drawable.tref;
        SkockoEnum.PIK -> Res.drawable.pik;
        SkockoEnum.HERC -> Res.drawable.herc;
        SkockoEnum.KARO -> Res.drawable.karo;
        SkockoEnum.ZVEZDA -> Res.drawable.zvezda;
    }
}


@Composable
fun ResultItem(modifier: Modifier, color: Color) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(5.dp)
    )
    {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .border(width = 1.dp, color = Color(0xFFCDE1F1), shape = CircleShape)
                .clip(CircleShape)
                .align(Alignment.Center)
                .background(color = color),

            )
    }
}


