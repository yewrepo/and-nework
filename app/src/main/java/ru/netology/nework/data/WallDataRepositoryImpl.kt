package ru.netology.nework.data

import ru.netology.nework.domain.WallDataRemoteSource
import ru.netology.nework.domain.WallDataRepository
import ru.netology.nework.model.post.Post

class WallDataRepositoryImpl(
    private val remoteSource: WallDataRemoteSource
) : WallDataRepository {
    override suspend fun getAuthorWall(authorId: Long): List<Post> {
        return remoteSource.getLatest(authorId)
    }
}