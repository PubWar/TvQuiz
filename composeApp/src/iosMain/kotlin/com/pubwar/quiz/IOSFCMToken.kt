package com.pubwar.quiz

import com.pubwar.quiz.auth.FCMToken
import cocoapods.FirebaseMessaging.*
import kotlinx.cinterop.ExperimentalForeignApi

class IOSFCMToken : FCMToken {
    @OptIn(ExperimentalForeignApi::class)
    override fun getToken(token: (String) -> Unit, error: (String) -> Unit) {
        FIRMessaging.messaging().tokenWithCompletion { t, e ->
            if (e != null) {
                error(e.localizedDescription)
            } else {
                if (t != null) {
                    token(t)
                } else {
                    error("FCM token is null")
                }
            }
        }
    }
}