package ru.netology.nework.data.remote.post

import ru.netology.nework.app.getOrThrow
import ru.netology.nework.data.PostToDtoMapper
import ru.netology.nework.data.handleError
import ru.netology.nework.data.network.NeWorkApi
import ru.netology.nework.domain.PostDataRemoteSource
import ru.netology.nework.model.post.Post

class RetrofitPostDataSourceImpl(
    private val api: NeWorkApi
) : PostDataRemoteSource {

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

    override suspend fun unlikePost(postId: Long): Post {
        return handleError {
            return@handleError api
                .unlikePost(postId)
                .getOrThrow()
                .let {
                    PostToDtoMapper().transform(it)
                }
        }
    }

    override suspend fun likePost(postId: Long): Post {
        return handleError {
            return@handleError api
                .likePost(postId)
                .getOrThrow()
                .let {
                    PostToDtoMapper().transform(it)
                }
        }
    }
}