package ru.netology.nework.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.netology.nework.data.remote.PostRemoteMediator
import ru.netology.nework.domain.PostDataLocalSource
import ru.netology.nework.domain.PostDataRemoteSource
import ru.netology.nework.domain.PostDataRepository
import ru.netology.nework.model.post.Post

class PostDataRepositoryImpl(
    private val remoteSource: PostDataRemoteSource,
    private val localSource: PostDataLocalSource,
    mediator: PostRemoteMediator
) : PostDataRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = 0,
        remoteMediator = mediator,
        pagingSourceFactory = localSource::pagingSource
    ).flow

    override fun getNewerCount(id: Long): Flow<Int> = flow {
        remoteSource.getNewer(id).let {
            emit(it.size)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun getAll(): List<Post> {
        remoteSource.getAll().let {
            localSource.save(it)
            return it
        }
    }

    override suspend fun unlikePost(postId: Long): Post {
        remoteSource.unlikePost(postId).let {
            localSource.save(it)
            return it
        }
    }

    override suspend fun likePost(postId: Long): Post {
        remoteSource.likePost(postId).let {
            localSource.save(it)
            return it
        }
    }
}