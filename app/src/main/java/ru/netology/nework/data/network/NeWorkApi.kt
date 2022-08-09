package ru.netology.nework.data.network

import retrofit2.Response
import retrofit2.http.*
import ru.netology.nework.data.remote.PostRemote
import ru.netology.nework.data.remote.UserRemote

interface NeWorkApi {

    @GET("api/users/")
    suspend fun getUsers(): Response<List<UserRemote>>

    @GET("api/users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: Long): Response<UserRemote>

    @GET("api/posts/")
    suspend fun getPosts(): Response<List<PostRemote>>

    @GET("api/posts/latest/")
    suspend fun getLatest(@Query("count") count: Int): Response<List<PostRemote>>

    @GET("posts/{post_id}/newer")
    suspend fun getNewer(@Path("post_id") postId: Long): Response<List<PostRemote>>

    @GET("api/posts/{post_id}/after/")
    suspend fun getPostsAfter(
        @Path("post_id") postId: Long,
        @Query("count") count: Int
    ): Response<List<PostRemote>>

    @GET("api/posts/{post_id}/before/")
    suspend fun getPostsBefore(
        @Path("post_id") postId: Long,
        @Query("count") count: Int
    ): Response<List<PostRemote>>

    @GET("api/my/wall/")
    suspend fun getMyWall(): Response<List<PostRemote>>

    @GET("api/{author_id}/wall/")
    suspend fun getAuthorWall(@Path("author_id") authorId: Int): Response<List<PostRemote>>

    @GET("api/{author_id}/wall/latest")
    suspend fun getAuthorWallLatest(
        @Query("count") count: Int,
        @Path("author_id") postId: Int
    ): Response<List<PostRemote>>

    @GET("api/{author_id}/wall/{post_id}/after")
    suspend fun getAuthorWallAfter(
        @Path("author_id") authorId: Int,
        @Path("post_id") postId: Long
    ): Response<List<PostRemote>>

    @GET("api/{author_id}/wall/{post_id}/before")
    suspend fun getAuthorWallBefore(
        @Query("count") count: Int,
        @Path("author_id") authorId: Int,
        @Path("post_id") postId: Long
    ): Response<List<PostRemote>>

   @GET("api/{author_id}/wall/{post_id}/newer")
    suspend fun getAuthorWallNewer(
        @Path("author_id") authorId: Int,
        @Path("post_id") postId: Long
    ): Response<List<PostRemote>>

    @POST("/api/posts/{post_id}/likes/")
    suspend fun likePost(
        @Path("post_id") postId: Long
    ): Response<PostRemote>

    @DELETE("/api/posts/{post_id}/likes/")
    suspend fun unlikePost(
        @Path("post_id") postId: Long
    ): Response<PostRemote>

}