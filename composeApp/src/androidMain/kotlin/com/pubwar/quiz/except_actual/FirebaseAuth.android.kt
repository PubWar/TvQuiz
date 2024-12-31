package com.pubwar.quiz.except_actual


import com.pubwar.quiz.AndroidFCMToken
import com.pubwar.quiz.AndroidPhoneAuth
import com.pubwar.quiz.auth.FCMToken
import com.pubwar.quiz.auth.PhoneAuth

actual fun getPhoneAuth(): PhoneAuth = AndroidPhoneAuth()
actual fun getFCMToken(): FCMToken = AndroidFCMToken()