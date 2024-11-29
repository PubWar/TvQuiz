package com.pubwar.quiz

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pubwar.quiz.io.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AndroidDataStoreManager(private val context: Context) : DataStoreManager {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    override suspend fun saveValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override suspend fun getValue(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.map { it[dataStoreKey] }.first()
        return preferences
    }
}