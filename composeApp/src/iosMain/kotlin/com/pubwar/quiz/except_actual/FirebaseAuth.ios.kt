package com.pubwar.quiz.except_actual

import com.pubwar.quiz.IOSFCMToken
import com.pubwar.quiz.IOSPhoneAuth
import com.pubwar.quiz.auth.FCMToken
import com.pubwar.quiz.auth.PhoneAuth

actual fun getPhoneAuth(): PhoneAuth = IOSPhoneAuth()
actual fun getFCMToken(): FCMToken = IOSFCMToken()