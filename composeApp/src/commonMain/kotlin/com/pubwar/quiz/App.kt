package com.pubwar.quiz



import LoginScreen
import QrScannerView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pubwar.quiz.ui.screens.intro.IntroScreen
import com.pubwar.quiz.ui.screens.quiz.Quiz
import com.pubwar.quiz.ui.theme.IntroTheme
import com.pubwar.quiz.ui.theme.MyAppTheme
import com.pubwar.quiz.ui.view_models.IntroViewModel
import com.pubwar.quiz.ui.view_models.LoginViewModel
import com.pubwar.quiz.ui.view_models.QuizViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf


@OptIn(KoinExperimentalAPI::class, DelicateCoroutinesApi::class)
@Composable
fun App() {
    KoinContext {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        NavHost(
            navController = navController,
            startDestination = "/login"
        ) {

            composable(route = "/login")
            {
                IntroTheme {
                    LoginScreen(viewModel = koinViewModel<LoginViewModel>()){
                        navController.navigate("/intro")
                    }
                }
            }

            composable(route = "/quiz/{quizId}/{seconds}") { backStackEntry ->
                MyAppTheme {
                    val seconds: String? = backStackEntry.arguments?.getString("seconds")
                    val quizId: String? = backStackEntry.arguments?.getString("quizId")
                    if (seconds != null) {
                        val startIn = seconds.filter { it.isDigit() }.toInt()

                        println("start quiz $quizId in $startIn seconds")
                        Quiz(quizViewModel = koinViewModel<QuizViewModel>(parameters = {
                            parametersOf(quizId, startIn)
                        }))
                    }
                }
            }

            composable(route = "/qrscanner") {

                MyAppTheme {
//                    Quiz(quizViewModel = koinViewModel<QuizViewModel>(parameters = {
//                        parametersOf(0)
//                    }))

//                    Asocijacije()
                        QrScannerView { quizId, startIn ->
//                            val startIn = it.filter { it.isDigit() }.toInt()
                            GlobalScope.launch(Dispatchers.Main) {
                                navController.navigate("/quiz/$quizId/${startIn * 1000}"){
                                    launchSingleTop = true
                                    popUpTo("/qrscanner") {
                                        inclusive = true
                                    }
                                }

                            }
                        }
                }
            }


            composable(route = "/intro") {
                IntroTheme {
                        IntroScreen(viewModel = koinViewModel<IntroViewModel>(), navController)
                }

            }
        }
    }
}





