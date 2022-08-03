package ru.netology.nework.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.netology.nework.app.NetworkError
import ru.netology.nework.app.UnknownError
import ru.netology.nework.app.getOrThrow
import ru.netology.nework.data.PostToDtoMapper
import ru.netology.nework.data.network.NeWorkApi
import ru.netology.nework.domain.PostDataRemoteSource
import ru.netology.nework.model.post.Post
import java.io.IOException

class RetrofitPostDataSourceImpl(
    private val api: NeWorkApi
) : PostDataRemoteSource {

    override fun get(): Flow<List<Post>> = flow { }

    override suspend fun getNewer(id: Long): List<Post> {
        return handleError {
            return@handleError api
                .getNewer(id)
                .getOrThrow()
                .map {
                    return@map PostToDtoMapper().transform(it)
                }
        }
    }

    override suspend fun getAll(): List<Post> {
        return handleError {
            return@handleError api
                .getPosts()
                .getOrThrow()
                .map {
                    return@map PostToDtoMapper().transform(it)
                }
        }
    }

    private suspend fun <T> handleError(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}