package ru.netology.nework.model

data class RegistrationState(
    val nameOk: Boolean = false,
    val loginOk: Boolean = false,
    val passwordOk: Boolean = false,
    val name: String = "",
    val login: String = "",
    val password: String = ""
)