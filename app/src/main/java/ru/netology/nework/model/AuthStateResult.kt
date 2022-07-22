package ru.netology.nework.model

data class AuthStateResult(
    val code: Int = -1,
    val isSuccess: Boolean = false,
    val isNetworkError: Boolean = false,
    val message: String = ""
)