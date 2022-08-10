package ru.netology.nework.domain

import ru.netology.nework.model.post.Post

interface WallDataRemoteSource {
    suspend fun getLatest(authorId: Long): List<Post>
}