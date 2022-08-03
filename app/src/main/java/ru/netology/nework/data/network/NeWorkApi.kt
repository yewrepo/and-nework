package ru.netology.nework.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.netology.nework.data.remote.PostRemote

interface NeWorkApi {

    @GET("api/my/wall/")
    suspend fun getMyWall(): Response<List<PostRemote>>

    @GET("api/posts/")
    suspend fun getPosts(): Response<List<PostRemote>>

    @GET("api/posts/latest/")
    suspend fun getLatest(): Response<List<PostRemote>>

    @GET("posts/{post_id}/newer")
    suspend fun getNewer(@Path("post_id") postId: Long): Response<List<PostRemote>>

    @GET("api/posts/{post_id}/after/")
    suspend fun getPostsAfter(@Path("post_id") postId: Long): Response<List<PostRemote>>

    @GET("api/posts/{post_id}/before/")
    suspend fun getPostsBefore(@Path("post_id") postId: Long): Response<List<PostRemote>>
}