package ru.netology.nework.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.nework.domain.TokenDataSource

class NeWorkApiInterceptor(
    tokenApi: TokenApi,
    private val tokenSource: TokenDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenData = tokenSource.getToken()
        if (tokenData?.token == null) {
            chain.request().newBuilder().build()
        } else {
            chain.request().newBuilder()
                .addHeader("Authorization", tokenData.token)
                .build()
        }.also {
            return chain.proceed(it)
        }
    }
}
