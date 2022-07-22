package ru.netology.nework.app

object Success : Status()
object Error : Status()
object Loading : Status()

sealed class Status

class Resource<T> private constructor(
    val status: Status,
    val data: T?,
    val t: Throwable?
) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Success,
                data,
                null
            )
        }

        fun <T> error(t: Throwable): Resource<T> {
            return Resource(
                Error,
                null,
                t
            )
        }

        fun <T : Any> loading(): Resource<T> {
            return Resource(
                Loading,
                null,
                null
            )
        }
    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, throwable=$t)"
    }
}
