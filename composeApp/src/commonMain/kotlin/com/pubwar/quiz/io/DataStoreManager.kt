package com.pubwar.quiz.io

interface DataStoreManager {
    suspend fun saveValue(key: String, value: String)
    suspend fun getValue(key: String): String?
}