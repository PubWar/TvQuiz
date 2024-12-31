package com.pubwar.quiz

import com.google.firebase.messaging.FirebaseMessaging
import com.pubwar.quiz.auth.FCMToken

class AndroidFCMToken: FCMToken {
    override fun getToken(token: (String) -> Unit, error: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                token(task.result)
            } else {
                error("Fetching FCM token failed: ${task.exception}")
            }

        }
    }
}