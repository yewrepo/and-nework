package ru.netology.nework.data.local

import android.content.Context
import com.google.gson.Gson
import ru.netology.nework.data.Constant
import ru.netology.nework.domain.TokenDataSource
import ru.netology.nework.model.user.Token

class TokenDataSourceImpl(
    c: Context
) : TokenDataSource {

    private val prefs = c.getSharedPreferences(Constant.PREFS_TOKEN, Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun getToken() = gson.fromJson(
        prefs.getString(Constant.PREFS_TOKEN_DATA, "{}"),
        Token::class.java
    )

    override fun setToken(token: Token) {
        prefs.edit().putString(Constant.PREFS_TOKEN_DATA, gson.toJson(token)).apply()
    }
}