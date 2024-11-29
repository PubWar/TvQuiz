package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.ui.screens.auth.LoginState
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.DelicateCryptographyApi
import dev.whyoleg.cryptography.algorithms.symmetric.AES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state

    fun onUsernameChanged(newUsername: String) {
        _state.update {
            it.copy(
                username = newUsername
            )
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _state.update {
            it.copy(
                password = newPassword
            )
        }
    }

//    suspend fun login() {
//        _state.update {
//            it.copy(
//                isLoading = true,
//                errorMessage = null
//            )
//        }
//        // Simulate login process
//        kotlinx.coroutines.delay(2000)
//        if (state.value.username == "user" && state.value.password == "password") {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    success = true
//                )
//            }
//        } else {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = "Invalid username or password"
//                )
//            }
//        }
//    }


    fun addPadding(data: ByteArray, blockSize: Int): ByteArray {
        val paddingSize = blockSize - (data.size % blockSize)
        return data + ByteArray(paddingSize) { paddingSize.toByte() }
    }

    @OptIn(DelicateCryptographyApi::class, ExperimentalStdlibApi::class)
    suspend fun encrypt(text : String) {
        // Your AES key and IV as hex strings
        val hexKey = "5DC069F784BA37BC8898384799DBFF9FED6B84CBD481A4793DB264FC8EE1D0B4"
        val hexIv = "000102030405060708090a0b0c0d0e0f"

        // Convert hex strings to ByteArray
        val keyBytes = hexKey.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val ivBytes = hexIv.chunked(2).map { it.toInt(16).toByte() }.toByteArray()

        // Initialize Cryptography
        val cryptography = CryptographyProvider.Default

        // Configure AES-CBC
        val aesCbc = cryptography.get(AES.CBC)

        // Import AES key
        val decoder = aesCbc.keyDecoder()
        val key = decoder.decodeFrom(AES.Key.Format.RAW, keyBytes)
        val cipher = key.cipher()

        // Example plaintext
        val plaintext = text.encodeToByteArray()
        val paddedPlaintext = addPadding(plaintext, 16)

        // Encrypt
        val ciphertext: ByteArray = cipher.encrypt(iv = ivBytes, plaintextInput = paddedPlaintext)

        println(ciphertext.toHexString(HexFormat.UpperCase))
    }

    @OptIn(DelicateCryptographyApi::class)
    suspend fun decrypt(text: String){
        // Your AES key and IV as hex strings
        val hexKey = "5DC069F784BA37BC8898384799DBFF9FED6B84CBD481A4793DB264FC8EE1D0B4"
        val hexIv = "000102030405060708090a0b0c0d0e0f"

        // Convert hex strings to ByteArray
        val keyBytes = hexKey.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val ivBytes = hexIv.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val ciphertext = text.chunked(2).map { it.toInt(16).toByte() }.toByteArray()

        // Initialize Cryptography
        val cryptography = CryptographyProvider.Default

        // Configure AES-CBC
        val aesCbc = cryptography.get(AES.CBC)

        // Import AES key
        val decoder = aesCbc.keyDecoder()
        val key = decoder.decodeFrom(AES.Key.Format.RAW, keyBytes)
        val cipher = key.cipher()

        // Decrypt the ciphertext
        val decryptedPaddedBytes = cipher.decrypt(ivBytes, ciphertext)

        // Remove padding
//        val decryptedBytes = removePadding(decryptedPaddedBytes)
        println(decryptedPaddedBytes.decodeToString())
    }

    @OptIn(DelicateCryptographyApi::class)
    fun login() = viewModelScope.launch {
                        _state.update {
                    it.copy(
                        isLoading = false,
                        success = true
                    )
                }
//
//        _state.update {
//            it.copy(
//                isLoading = true,
//                errorMessage = null
//            )
//        }
//        loginRepo.login(state.value.username, state.value.password)
//            .onSuccess {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        success = true
//                    )
//                }
//            }
//            .onError {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = "Invalid username or password"
//                    )
//                }
//            }
    }
}

