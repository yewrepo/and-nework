package ru.netology.nework.domain

import ru.netology.nework.model.post.Post

interface PostDataRemoteSource {
    suspend fun getNewer(id: Long): List<Post>
    suspend fun getAll(): List<Post>
    suspend fun unlikePost(postId: Long): Post
    suspend fun likePost(postId: Long): Post
}