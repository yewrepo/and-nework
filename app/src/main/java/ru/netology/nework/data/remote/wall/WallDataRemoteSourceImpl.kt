package ru.netology.nework.data.remote.wall

import ru.netology.nework.app.getOrThrow
import ru.netology.nework.data.PostToDtoMapper
import ru.netology.nework.data.handleError
import ru.netology.nework.data.network.Constants
import ru.netology.nework.data.network.NeWorkApi
import ru.netology.nework.domain.WallDataRemoteSource
import ru.netology.nework.model.post.Post

class WallDataRemoteSourceImpl(
    private val api: NeWorkApi
) : WallDataRemoteSource {

    override suspend fun getLatest(authorId: Long): List<Post> {
        return handleError {
            return@handleError api
                .getAuthorWallLatest(
                    authorId, Constants.DEFAULT_PAGE_SIZE
                )
                .getOrThrow()
                .map {
                    return@map PostToDtoMapper().transform(it)
                }
        }
    }
}