package ru.netology.nework.domain

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.model.post.Post

interface PostDataRemoteSource {
    fun get(): Flow<List<Post>>
    suspend fun getNewer(id: Long):  List<Post>
    suspend fun getAll(): List<Post>
}