package ru.netology.nework.data.local

import android.content.Context
import ru.netology.nework.data.Constant
import ru.netology.nework.domain.TokenDataSource

class TokenDataSourceImpl(
    c: Context
) : TokenDataSource {

    private val prefs = c.getSharedPreferences(Constant.PREFS_TOKEN, Context.MODE_PRIVATE)

    override fun getToken() = prefs.getString(Constant.PREFS_TOKEN_DATA, null)

    override fun setToken(token: String) {
        prefs.edit().putString(Constant.PREFS_TOKEN_DATA, token).apply()
    }
}