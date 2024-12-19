package com.pubwar.quiz



import LoginScreen
import QrScannerView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
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
import org.koin.compose.koinInject
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
//                    Quiz(quizViewModel = koinViewModel<QuizViewModel>(parameters = {
//                        parametersOf(0)
//                    }))

//                    Asocijacije()
                        QrScannerView { it ->
                            val startIn = it.filter { it.isDigit() }.toInt()
                            GlobalScope.launch(Dispatchers.Main) {

                                navController.navigate("/quiz/${startIn * 1000}")
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





