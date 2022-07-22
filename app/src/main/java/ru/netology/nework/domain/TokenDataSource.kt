package ru.netology.nework.domain

interface TokenDataSource {
    fun getToken(): String?

    fun setToken(token: String)
}