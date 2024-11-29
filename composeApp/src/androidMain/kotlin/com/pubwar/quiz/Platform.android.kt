package com.pubwar.quiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import okio.Path.Companion.toPath


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}

actual fun getPlatformDataManager(context: Any?): DataStore<Preferences> {
    require(context is Context) { "Expected Android Context" }
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }
    )
}
@Composable
actual fun VideoPlayer(modifier: Modifier, path: String){
    val path = ("android.resource://com.pubwar.quiz" ).toString() + "/" + R.raw.comercial

    val context = LocalContext.current

    val uri = Uri.parse(path)
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(uri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE // Set to loop the video
        }
    }

        AndroidView(
            modifier = modifier,
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                }
            },
        )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }


//    AndroidView(
//            factory = { context ->
//                VideoView(context).apply {
//                    setVideoURI(uri)
////                val mediaController = MediaController(context)
////                mediaController.setAnchorView(this)
////                setMediaController(mediaController)
//                    start()
//                    setOnPreparedListener {
//                        it.isLooping = true
//                    }
//                }
//            },
//            update = {})
    }


actual fun getSpeechToTextManager(context: Any?): SpeechToTextManager {
    require(context is Context) { "Expected Android Context" }
    var myListener: ((String) -> Unit)? = null

//     var listener: ((String) -> Unit)? = null

      val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onResults(bundle: Bundle?) {
                    val result = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()
                    result?.let {
                        myListener?.invoke(it)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {
//                    println("onPartialResults")

//                    val partialText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//                    partialText?.let {
//                        println("Partial result: ${it.firstOrNull()}")
//                        // Optionally provide feedback to the user (e.g., showing in UI)
//                    }
                }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    println("onEvent $eventType")
                }

                override fun onError(error: Int) {

                    when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> {
                            // Handle the no match error
                            println("Error: No match found.")
                        }
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                            // Handle timeout error
                            println("Error: Speech input timeout.")
                        }
                        // Handle other error cases
                        else -> {
                            println("Error code: $error")
                        }
                    }
                }

                override fun onReadyForSpeech(params: Bundle?) {
                        println("onReadyForSpeech")
                }

                override fun onBeginningOfSpeech() {
                    println("onBeginningOfSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
//                    println("onRmsChanged")
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                    println("onBufferReceived")
                }

                override fun onEndOfSpeech() {
                    println("onEndOfSpeech")
                }

                // Implement other necessary overrides like onError, onReadyForSpeech, etc.
            })
        }


    val speechToTextManager = object : SpeechToTextManager() {



        override fun startListening() {
            // Provide the logic for starting speech recognition
            println("Listening started...")

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sr-RS")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)  // Use free-form language model
//                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
//                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, false)

            }
            speechRecognizer.startListening(intent)
        }

        override fun stopListening() {
            // Provide the logic for stopping speech recognition
            println("Listening stopped.")
            speechRecognizer.stopListening()
        }

        override fun setOnResultListener(listener: (String) -> Unit) {
            myListener = listener
            println("Listener set.")
        }
    }

    return speechToTextManager

}

actual fun checkAndRequestAudioPermission(onPermissionResult: (Boolean) -> Unit) {
}