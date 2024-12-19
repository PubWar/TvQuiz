package com.pubwar.quiz.ui.view_models

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.core.Timer
import com.pubwar.quiz.domain.repos.ActiveQuizRepo
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.ui.screens.intro.IntroState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IntroViewModel(
    private val activeQuizRepo: ActiveQuizRepo,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(IntroState())
    val state = _state
    init {
        viewModelScope.launch {
            val quizId = activeQuizRepo.getQuizId()
            activeQuizRepo.getExpiredTime(quizId).let { expired ->
                if (expired[0] > 0) {
                    _state.update {
                        it.copy(
                            activeQuiz = true,
                            activeQuizExpired = expired[0].toInt(),
                            activeQuizStarted = expired[1]
                        )
                    }
                }
            }
            _state.update {
                it.copy(
                    username = localDataSource.getUsername()
                )
            }
        }
    }
}