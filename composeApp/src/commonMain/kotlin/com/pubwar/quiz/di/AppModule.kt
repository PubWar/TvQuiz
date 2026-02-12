package com.pubwar.quiz.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.pubwar.quiz.auth.FCMToken
import com.pubwar.quiz.auth.PhoneAuth
import com.pubwar.quiz.core.data.HttpClientFactory
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.repos.ActiveQuizRepo
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.except_actual.getFCMToken
import com.pubwar.quiz.except_actual.getPhoneAuth
import com.pubwar.quiz.getPlatformDataManager
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.io.locale_data_source.LocalDataSourceImpl
import com.pubwar.quiz.io.network.RemoteDataSource
import com.pubwar.quiz.io.network.RemoteDataSourceImpl
import com.pubwar.quiz.io.repository.ActiveQuizRepoImpl
import com.pubwar.quiz.io.repository.LoginRepoImpl
import com.pubwar.quiz.io.repository.QuizRepoImpl
import com.pubwar.quiz.ui.view_models.IntroViewModel
import com.pubwar.quiz.ui.view_models.KoZnaZnaViewModel
import com.pubwar.quiz.ui.view_models.LoginViewModel
import com.pubwar.quiz.ui.view_models.OrderAnswersViewModel
import com.pubwar.quiz.ui.view_models.QuizViewModel
import com.pubwar.quiz.ui.view_models.TypeAnswerViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module


val appModule = module {

    single<DataStore<Preferences>>{ getPlatformDataManager(null) }
    single<PhoneAuth>{getPhoneAuth()}
    single<FCMToken>{ getFCMToken() }

    single { HttpClientFactory.create(get()) }

    singleOf(::LocalDataSourceImpl).bind<LocalDataSource>()


    singleOf(::RemoteDataSourceImpl).bind<RemoteDataSource>()
    singleOf(::QuizRepoImpl).bind<QuizRepository>()

    singleOf(::LoginRepoImpl).bind<LoginRepository>()
    singleOf(::ActiveQuizRepoImpl).bind<ActiveQuizRepo>()

    viewModel{ (quizId: String, startIn: Long) -> QuizViewModel(quizId, startIn, quizRepository = get()) }
    viewModel{ LoginViewModel(loginRepo = get())}
    viewModel{ IntroViewModel(activeQuizRepo = get(), localDataSource = get())}

    viewModel { (game: Game?) -> KoZnaZnaViewModel(game, get()) }
    viewModel { (game: Game?) -> OrderAnswersViewModel(game, get()) }
    viewModel { (game: Game?) -> TypeAnswerViewModel(game, get()) }

}
