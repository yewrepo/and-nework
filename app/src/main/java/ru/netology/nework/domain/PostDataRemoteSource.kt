package ru.netology.nework.domain

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.model.post.Post

interface PostDataRemoteSource {
    fun get(): Flow<List<Post>>
    suspend fun getNewer(id: Long):  List<Post>
    suspend fun getAll(): List<Post>
    suspend fun unlikePost(postId: Long): Post
    suspend fun likePost(postId: Long): Post
}