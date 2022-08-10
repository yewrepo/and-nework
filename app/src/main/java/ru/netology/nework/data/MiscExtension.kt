package ru.netology.nework.data

import ru.netology.nework.app.NetworkError
import ru.netology.nework.app.UnknownError
import java.io.IOException


suspend fun <T> handleError(block: suspend () -> T): T {
    try {
        return block()
    } catch (e: IOException) {
        throw NetworkError
    } catch (e: Exception) {
        throw UnknownError
    }
}