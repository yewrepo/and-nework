package ru.netology.nework.app

import com.google.gson.Gson
import retrofit2.Response
import ru.netology.nework.data.remote.ErrorRemote

sealed class AppError(override var message: String) : RuntimeException()
class ApiError(val status: Int, error: ErrorRemote) : AppError(error.reason)

fun <T> Response<T>.getOrThrow(): T {
    if (isSuccessful.not()) {
        throw errorBody()?.let { body ->
            ApiError(code(), Gson().fromJson(body.string(), ErrorRemote::class.java))
        } ?: ApiError(code(), ErrorRemote(message()))
    }
    return body() ?: throw ApiError(
        code(),
        ErrorRemote(message())
    )
}
