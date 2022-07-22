package ru.netology.nework.data.remote

data class UserRemote(
    val id: Int,
    val login: String,
    val name: String,
    val avatar : String?,
)
