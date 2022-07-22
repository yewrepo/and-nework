package ru.netology.nework.data.network

import retrofit2.http.GET
import ru.netology.nework.data.remote.PostRemote

interface NeWorkApi {

    @GET("api/my/wall/")
    fun getMyWall(): List<PostRemote>
}