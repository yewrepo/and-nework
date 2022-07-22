package ru.netology.nework.data.network

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import ru.netology.nework.data.remote.AuthRequestData
import ru.netology.nework.data.remote.TokenRemote

interface TokenApi {

    @POST("/api/users/registration/")
    suspend fun registration(
        @Field("login") login: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): TokenRemote

    @POST("/api/users/authentication/")
    suspend fun authentication(
        @Body data: AuthRequestData
    ): TokenRemote
}