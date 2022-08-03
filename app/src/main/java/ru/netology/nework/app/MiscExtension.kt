package ru.netology.nework.app

import android.view.LayoutInflater
import android.view.View
import com.google.gson.Gson
import retrofit2.Response
import ru.netology.nework.data.remote.ErrorRemote

sealed class AppError(override var message: String) : RuntimeException()
class ApiError(val status: Int, error: ErrorRemote) : AppError(error.reason)
object NetworkError : AppError("error_network")
object UnknownError : AppError("error_unknown")

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

fun View.getInflater() = LayoutInflater.from(context)
