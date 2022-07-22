package ru.netology.nework.data.network

import okhttp3.Interceptor
import okhttp3.Response

class NeWorkApiInterceptor(tokenApi: TokenApi) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}
