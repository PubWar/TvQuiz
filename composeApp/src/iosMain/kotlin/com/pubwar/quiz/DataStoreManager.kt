package com.pubwar.quiz

import com.pubwar.quiz.io.DataStoreManager

class IOSDataStoreManager: DataStoreManager {
    override suspend fun saveValue(key: String, value: String) {
        // Implement iOS-specific storage, e.g., UserDefaults
    }

    override suspend fun getValue(key: String): String? {
        // Implement iOS-specific retrieval, e.g., UserDefaults
        return null
    }
}