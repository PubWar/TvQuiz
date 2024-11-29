package com.pubwar.quiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getCurrentTime(): Long

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
expect fun getPlatformDataManager(context: Any?):DataStore<Preferences>


@Composable
expect fun VideoPlayer(modifier: Modifier, path: String)


abstract  class SpeechToTextManager {
    abstract fun startListening()
    abstract fun stopListening()
    abstract fun setOnResultListener(listener: (String) -> Unit)
}

expect fun getSpeechToTextManager(context: Any?) : SpeechToTextManager

expect fun checkAndRequestAudioPermission(onPermissionResult: (Boolean) -> Unit)
