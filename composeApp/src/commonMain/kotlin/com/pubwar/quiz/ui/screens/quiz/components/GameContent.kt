package com.pubwar.quiz.ui.screens.quiz.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.model.ViewType
import com.pubwar.quiz.ui.screens.quiz.games.Asocijacije
import com.pubwar.quiz.ui.screens.quiz.games.KoZnaZna
import com.pubwar.quiz.ui.screens.quiz.games.KorakPoKorak
import com.pubwar.quiz.ui.screens.quiz.games.MojBroj
import com.pubwar.quiz.ui.screens.quiz.games.NonGameView
import com.pubwar.quiz.ui.screens.quiz.games.OrderAnswers
import com.pubwar.quiz.ui.screens.quiz.games.Skocko
import com.pubwar.quiz.ui.screens.quiz.games.Slagalica
import com.pubwar.quiz.ui.screens.quiz.games.Spojnice
import com.pubwar.quiz.ui.screens.quiz.games.TypeAnswer
import com.pubwar.quiz.ui.view_models.AsocijacijeViewModel
import com.pubwar.quiz.ui.view_models.KoZnaZnaViewModel
import com.pubwar.quiz.ui.view_models.KorakPoKorakViewModel
import com.pubwar.quiz.ui.view_models.MojBrojViewModel
import com.pubwar.quiz.ui.view_models.OrderAnswersViewModel
import com.pubwar.quiz.ui.view_models.SkockoViewModel
import com.pubwar.quiz.ui.view_models.SlagalicaViewModel
import com.pubwar.quiz.ui.view_models.SpojniceViewModel
import com.pubwar.quiz.ui.view_models.TypeAnswerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameContent(viewType: ViewType, game: Game?, games: List<Game>, finishGame: () -> Unit) {
    val scope = rememberCoroutineScope()
    fun userFinishTheGame() {
        scope.launch {
            delay(1.seconds)
            finishGame()
        }
    }

    println("GameContents $viewType")
    when (viewType) {
        ViewType.UVOD -> NonGameView()
        ViewType.REKLAME -> NonGameView()
//        ViewType.SLAGALICA -> Slagalica(koinViewModel<SlagalicaViewModel>(parameters = { parametersOf(game.letters) })) { userFinishTheGame() }
        ViewType.SLAGALICA -> Slagalica(SlagalicaViewModel(game)) { userFinishTheGame() }
        ViewType.MOJ_BROJ -> MojBroj(MojBrojViewModel(game)) { userFinishTheGame() }
        ViewType.KORAK_PO_KORAK -> KorakPoKorak(KorakPoKorakViewModel(game)) { userFinishTheGame() }
        ViewType.SKOCKO -> Skocko(SkockoViewModel(game)) { userFinishTheGame() }
        ViewType.SPOJNICE -> Spojnice(SpojniceViewModel(game)){ userFinishTheGame() }
        ViewType.KO_ZNA_ZNA -> KoZnaZna(KoZnaZnaViewModel(game)){ userFinishTheGame() }
        ViewType.ASOCIJACIJE -> Asocijacije(AsocijacijeViewModel(game))
        ViewType.TYPE_ANSWER -> TypeAnswer(TypeAnswerViewModel(game)){userFinishTheGame()}
        ViewType.ORDER_ANSWERS -> OrderAnswers(OrderAnswersViewModel(game)){userFinishTheGame()}
        ViewType.KRAJ -> ResultView(games)
    }
}