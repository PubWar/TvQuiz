package com.pubwar.quiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIDevice


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getCurrentTime(): Long {
   return  NSDate().timeIntervalSince1970.toLong() * 1000
}

@OptIn(ExperimentalForeignApi::class)
actual fun getPlatformDataManager(context: Any?): DataStore<Preferences> {

        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
    val path = (requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME").toPath()

    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            path
        }
    )


}


@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    path: String
) {
}

actual fun getSpeechToTextManager(context: Any?): SpeechToTextManager {
    TODO("Not yet implemented")
}

actual fun checkAndRequestAudioPermission(onPermissionResult: (Boolean) -> Unit) {
}