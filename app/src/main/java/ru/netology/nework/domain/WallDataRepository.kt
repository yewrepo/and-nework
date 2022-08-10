package ru.netology.nework.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.model.post.Post

interface WallDataRepository {
    fun getAuthorWall(authorId: Long): Flow<PagingData<Post>>
}