package ru.netology.nework.data.network

import okhttp3.Interceptor
import okhttp3.Response

class NeWorkApiInterceptor(buildTokenApi: NeWorkApi) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}
