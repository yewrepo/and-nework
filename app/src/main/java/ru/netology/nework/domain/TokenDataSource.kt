package ru.netology.nework.domain

import ru.netology.nework.model.user.Token

interface TokenDataSource {
    fun getToken(): Token?

    fun setToken(token: Token)
}