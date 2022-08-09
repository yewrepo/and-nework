package ru.netology.nework.domain

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.model.post.Post

interface PostDataLocalSource {
    fun get(): Flow<List<Post>>
    fun pagingSource(): PagingSource<Int, Post>
    suspend fun getNewer(id: Long): List<Post>
    suspend fun getAll(): List<Post>
    suspend fun save(post: List<Post>): List<Post>
    suspend fun save(post: Post): Post
}