package com.pubwar.quiz



import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pubwar.quiz.ui.screens.intro.IntroScreen
import com.pubwar.quiz.ui.screens.quiz.Quiz
import com.pubwar.quiz.ui.theme.IntroTheme
import com.pubwar.quiz.ui.theme.MyAppTheme
import com.pubwar.quiz.ui.view_models.LoginViewModel
import com.pubwar.quiz.ui.view_models.QuizViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf


@OptIn(KoinExperimentalAPI::class, DelicateCoroutinesApi::class)
@Composable
fun App() {
    KoinContext {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "/login"
        ) {

            composable(route = "/login")
            {
                MyAppTheme {
                    LoginScreen(viewModel = koinViewModel<LoginViewModel>()){
                        navController.navigate("/intro")
                    }
                }
            }

            composable(route = "/quiz/{seconds}?") { backStackEntry ->
                MyAppTheme {
                    val seconds: String? = backStackEntry.arguments?.getString("seconds")
                    if (seconds != null) {
                        val startIn = seconds.filter { it.isDigit() }.toInt()
                        println("start in $startIn seconds")
                        Quiz(quizViewModel = koinViewModel<QuizViewModel>(parameters = {
                            parametersOf(startIn)
                        }))
                    }
                }
            }

            composable(route = "/qrscanner") {
                MyAppTheme {
                    Quiz(quizViewModel = koinViewModel<QuizViewModel>(parameters = {
                        parametersOf(0)
                    }))

//                    Asocijacije()

//                        QrScannerView {
//                            GlobalScope.launch(Dispatchers.Main) {
//                                navController.navigate("/quiz/$it")
//                            }
//                        }
                }
            }


            composable(route = "/intro") {
                IntroTheme {
                        IntroScreen(navController)
                }

            }
        }
    }
}





