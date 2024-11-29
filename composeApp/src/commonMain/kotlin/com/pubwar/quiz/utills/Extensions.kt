package com.pubwar.quiz.utills



import com.pubwar.quiz.BuildKonfig.HEX_IV
import com.pubwar.quiz.BuildKonfig.HEX_KEY
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.DelicateCryptographyApi
import dev.whyoleg.cryptography.algorithms.symmetric.AES

fun String.toCyrilic(): String {
    val latinToCyrillicMap = mapOf(
        'A' to 'А', 'a' to 'а',
        'B' to 'Б', 'b' to 'б',
        'C' to 'Ц', 'c' to 'ц',
        'Č' to 'Ч', 'č' to 'ч',
        'Ć' to 'Ћ', 'ć' to 'ћ',
        'D' to 'Д', 'd' to 'д',
        "Dž" to 'Џ', "dž" to 'џ',
        'Đ' to 'Ђ', 'đ' to 'ђ',
        'E' to 'Е', 'e' to 'е',
        'F' to 'Ф', 'f' to 'ф',
        'G' to 'Г', 'g' to 'г',
        'H' to 'Х', 'h' to 'х',
        'I' to 'И', 'i' to 'и',
        'J' to 'Ј', 'j' to 'ј',
        'K' to 'К', 'k' to 'к',
        'L' to 'Л', 'l' to 'л',
        "Lj" to 'Љ', "lj" to 'љ',
        'M' to 'М', 'm' to 'м',
        'N' to 'Н', 'n' to 'н',
        "Nj" to 'Њ', "nj" to 'њ',
        'O' to 'О', 'o' to 'о',
        'P' to 'П', 'p' to 'п',
        'R' to 'Р', 'r' to 'р',
        'S' to 'С', 's' to 'с',
        'Š' to 'Ш', 'š' to 'ш',
        'T' to 'Т', 't' to 'т',
        'U' to 'У', 'u' to 'у',
        'V' to 'В', 'v' to 'в',
        'Z' to 'З', 'z' to 'з',
        'Ž' to 'Ж', 'ž' to 'ж'
    )


    val stringBuilder = StringBuilder()

    var i = 0
    while (i < this.length) {
        // Handle digraphs like Dž, Lj, Nj
        if (i + 1 < this.length) {
            val digraph = this.substring(i, i + 2)
            if (latinToCyrillicMap.containsKey(digraph)) {
                stringBuilder.append(latinToCyrillicMap[digraph])
                i += 2
                continue
            }
        }

        // Handle single characters
        stringBuilder.append(latinToCyrillicMap[this[i]] ?: this[i])
        i++
    }

    return stringBuilder.toString()
}

fun Int.formatDuration(): String {
    val minutes = (this / 60).toString().padStart(2, '0')
    val secs = (this % 60).toString().padStart(2, '0')

    return "$minutes:$secs"
}

fun String.toByteArray(): ByteArray {
    return this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}

@OptIn(DelicateCryptographyApi::class, ExperimentalStdlibApi::class)
suspend fun String.encrypt(): String
{
    // Initialize Cryptography
    val cryptography = CryptographyProvider.Default

    // Configure AES-CBC
    val aesCbc = cryptography.get(AES.CBC)

    // Import AES key
    val decoder = aesCbc.keyDecoder()
    val key = decoder.decodeFrom(AES.Key.Format.RAW, HEX_KEY.toByteArray())
    val cipher = key.cipher()

    // Example plaintext
    val plaintext = this.encodeToByteArray()
    val paddedPlaintext = addPadding(plaintext, 16)

    // Encrypt
    val ciphertext: ByteArray = cipher.encrypt(iv = HEX_IV.toByteArray(), plaintextInput = paddedPlaintext)

    return ciphertext.toHexString(HexFormat.UpperCase)
}


@OptIn(DelicateCryptographyApi::class)
suspend fun String.decrypt() : String{
    val ciphertext = this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    // Initialize Cryptography
    val cryptography = CryptographyProvider.Default
    // Configure AES-CBC
    val aesCbc = cryptography.get(AES.CBC)

    // Import AES key
    val decoder = aesCbc.keyDecoder()
    val key = decoder.decodeFrom(AES.Key.Format.RAW, HEX_KEY.toByteArray())
    val cipher = key.cipher()

    // Decrypt the ciphertext
    val decryptedPaddedBytes = cipher.decrypt(HEX_IV.toByteArray(), ciphertext)
    return decryptedPaddedBytes.decodeToString()
}


fun addPadding(data: ByteArray, blockSize: Int): ByteArray {
    val paddingSize = blockSize - (data.size % blockSize)
    return data + ByteArray(paddingSize) { paddingSize.toByte() }
}