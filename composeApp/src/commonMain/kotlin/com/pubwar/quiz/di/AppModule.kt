package com.pubwar.quiz.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.pubwar.quiz.core.data.HttpClientFactory
import com.pubwar.quiz.domain.repos.ActiveQuizRepo
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.getPlatformDataManager
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.io.locale_data_source.LocalDataSourceImpl
import com.pubwar.quiz.io.network.RemoteDataSource
import com.pubwar.quiz.io.network.RemoteDataSourceImpl
import com.pubwar.quiz.io.repository.ActiveQuizRepoImpl
import com.pubwar.quiz.io.repository.LoginRepoImpl
import com.pubwar.quiz.io.repository.QuizRepoImpl
import com.pubwar.quiz.ui.view_models.IntroViewModel
import com.pubwar.quiz.ui.view_models.LoginViewModel
import com.pubwar.quiz.ui.view_models.QuizViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module


val appModule = module {

    single<DataStore<Preferences>>{ getPlatformDataManager(null) }

    single { HttpClientFactory.create(get()) }

    singleOf(::LocalDataSourceImpl).bind<LocalDataSource>()

    singleOf(::RemoteDataSourceImpl).bind<RemoteDataSource>()
    singleOf(::QuizRepoImpl).bind<QuizRepository>()

    singleOf(::LoginRepoImpl).bind<LoginRepository>()
    singleOf(::ActiveQuizRepoImpl).bind<ActiveQuizRepo>()

    viewModel{ (startIn: Long) -> QuizViewModel(startIn, quizRepository = get()) }
    viewModel{ LoginViewModel(loginRepo = get())}
    viewModel{ IntroViewModel(activeQuizRepo = get(), localDataSource = get())}
}
