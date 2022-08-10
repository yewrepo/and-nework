package ru.netology.nework.domain

import ru.netology.nework.model.post.Post

interface WallDataRepository {
   suspend fun getAuthorWall(authorId: Long): List<Post>
}