package ru.netology.nework.data.local

import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.netology.nework.data.DtoToPostMapper
import ru.netology.nework.data.PostEntityListToDtoMapper
import ru.netology.nework.data.PostEntityToDtoMapper
import ru.netology.nework.data.db.PostDao
import ru.netology.nework.domain.PostDataLocalSource
import ru.netology.nework.model.post.Post

class RoomPostDataSourceImpl(
    private val dao: PostDao
) : PostDataLocalSource {
    override fun get(): Flow<List<Post>> = dao.getAll()
        .map {
            PostEntityListToDtoMapper().transform(it)
        }
        .asFlow()
        .flowOn(Dispatchers.Default)

    override fun pagingSource(): PagingSource<Int, Post> {
        return dao.pagingSource().map {
            PostEntityToDtoMapper().transform(it)
        }.asPagingSourceFactory(Dispatchers.IO).invoke()
    }

    override suspend fun getNewer(id: Long): List<Post> {
        return dao.getNewer().map {
            PostEntityToDtoMapper().transform(it)
        }
    }

    override suspend fun getAll(): List<Post> {
        return dao.getAll().value?.let { list ->
            return@let PostEntityListToDtoMapper().transform(list)
        } ?: emptyList()
    }

    override suspend fun save(post: List<Post>): List<Post> {
        dao.insert(post.map {
            DtoToPostMapper().transform(it)
        })
        return dao.getAll().value?.map {
            PostEntityToDtoMapper().transform(it)
        } ?: emptyList()
    }
}