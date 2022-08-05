package ru.netology.nework.app

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Response
import ru.netology.nework.data.remote.ErrorRemote
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed class AppError(override var message: String) : RuntimeException()
class ApiError(val status: Int, error: ErrorRemote) : AppError(error.reason)
object NetworkError : AppError("error_network")
object UnknownError : AppError("error_unknown")

fun LocalDateTime.toCommon(): String {
    return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm"))
}

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

fun RecyclerView.ViewHolder.getContext() = itemView.context!!
