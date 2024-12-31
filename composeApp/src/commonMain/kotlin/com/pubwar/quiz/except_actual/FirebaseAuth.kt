package com.pubwar.quiz.except_actual

import com.pubwar.quiz.auth.FCMToken
import com.pubwar.quiz.auth.PhoneAuth

expect fun getPhoneAuth(): PhoneAuth
expect fun getFCMToken(): FCMToken
