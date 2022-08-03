package ru.netology.nework.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.model.post.Post

interface PostDataRepository {
    val data: Flow<PagingData<Post>>
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun getAll(): List<Post>
}