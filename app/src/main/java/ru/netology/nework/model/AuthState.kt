package ru.netology.nework.model

data class AuthState(
    val loginOk: Boolean = false,
    val passwordOk: Boolean = false,
    val login: String = "",
    val password: String = ""
)