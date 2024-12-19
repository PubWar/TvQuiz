package com.pubwar.quiz.io.locale_data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val pref: DataStore<Preferences>
) : LocalDataSource {

    private val usernameKey = stringPreferencesKey("USERNAME")
    private val currentQuizId = stringPreferencesKey("QUIZ_ID")
    private val currentExpired = longPreferencesKey("EXPIRED")
    private val currentStartTime = longPreferencesKey("START_TIME")

    override suspend fun saveUsername(username: String) {
        pref.edit { dataStore ->
            dataStore[usernameKey] = username
        }
    }

    override suspend fun getUsername(): String {
        return pref.data.map {
            it[usernameKey]
        }.first() ?: ""
    }

    override suspend fun saveCurrentQuizConfiguration(configuration: String, quizId: String) {
        val key = stringPreferencesKey(quizId)
        pref.edit { dataStore ->
            dataStore[key] = configuration
        }
    }

    override suspend fun getCurrentQuizConfiguration(quizId: String): String? {
        val key = stringPreferencesKey(quizId)
        return pref.data.map {
            it[key]
        }.first()
    }

    override suspend fun clearActiveQuiz() {
        pref.edit { dataStore ->
            dataStore[currentQuizId] = ""
            dataStore[currentStartTime] = 0
            dataStore[currentExpired] = 0
        }
    }


    override suspend fun saveAccessToken(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun saveCurrentQuizId(id: String) {
        pref.edit { dataStore ->
            dataStore[currentQuizId] = id
        }
    }

    override suspend fun getCurrentQuizId(): String {
        return pref.data.map {
            it[currentQuizId]
        }.first().toString()
    }

    override suspend fun saveCurrentQuizStartTime(startTime: Long) {
        pref.edit { dataStore ->
            dataStore[currentStartTime] = startTime
        }
    }

    override suspend fun getCurrentQuizStartTime(): Long {
        return pref.data.map {
            it[currentStartTime]
        }.first() ?: 0
    }

    override suspend fun saveCurrentQuizExpiration(expiration: Long) {
        pref.edit { dataStore ->
            dataStore[currentExpired] = expiration
        }
    }

    override suspend fun getCurrentQuizExpiration(): Long {

        return pref.data.map { it[currentExpired] }.first() ?: 0
    }
}