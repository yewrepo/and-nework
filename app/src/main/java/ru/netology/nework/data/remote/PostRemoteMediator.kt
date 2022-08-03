package ru.netology.nework.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.Response
import ru.netology.nework.app.ApiError
import ru.netology.nework.data.PostToEntityMapper
import ru.netology.nework.data.db.AppDb
import ru.netology.nework.data.local.PostRemoteKeyEntity
import ru.netology.nework.data.network.NeWorkApi
import ru.netology.nework.model.post.Post
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator constructor(
    private val service: NeWorkApi,
    private val db: AppDb
) : RemoteMediator<Int, Post>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Post>
    ): MediatorResult {
        try {
            val postRemoteKeyDao = db.postRemoteKeyDao()
            val response = when (loadType) {
                LoadType.REFRESH -> {
                    val id = postRemoteKeyDao.max()
                    if (id == null) {
                        service.getLatest()
                    } else {
                        service.getPostsAfter(id)
                    }

                }
                LoadType.PREPEND -> {
                    Response.success(emptyList())
                }
                LoadType.APPEND -> {
                    val id = postRemoteKeyDao.min() ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )
                    service.getPostsBefore(id)
                }
            }

            if (!response.isSuccessful) {
                throw ApiError(response.code(), ErrorRemote(response.message()))
            }
            val body = response.body() ?: throw ApiError(
                response.code(),
                ErrorRemote(response.message()),
            )

            if (body.isNotEmpty()) {
                db.withTransaction {
                    when (loadType) {
                        LoadType.REFRESH -> {
                            if (postRemoteKeyDao.isEmpty()) {
                                postRemoteKeyDao.insert(
                                    listOf(
                                        PostRemoteKeyEntity(
                                            type = PostRemoteKeyEntity.KeyType.AFTER,
                                            id = body.first().id,
                                        ),
                                        PostRemoteKeyEntity(
                                            type = PostRemoteKeyEntity.KeyType.BEFORE,
                                            id = body.last().id,
                                        ),
                                    )
                                )
                            } else {
                                postRemoteKeyDao.insert(
                                    PostRemoteKeyEntity(
                                        type = PostRemoteKeyEntity.KeyType.AFTER,
                                        id = body.first().id,
                                    )
                                )
                            }
                        }
                        LoadType.PREPEND -> {
                            postRemoteKeyDao.insert(
                                PostRemoteKeyEntity(
                                    type = PostRemoteKeyEntity.KeyType.AFTER,
                                    id = body.first().id,
                                )
                            )
                        }
                        LoadType.APPEND -> {
                            postRemoteKeyDao.insert(
                                PostRemoteKeyEntity(
                                    type = PostRemoteKeyEntity.KeyType.BEFORE,
                                    id = body.last().id,
                                )
                            )
                        }
                    }
                    db.postDao().insert(PostToEntityMapper().transform(body))
                }
            }
            return MediatorResult.Success(endOfPaginationReached = body.isEmpty())
        } catch (e: Exception) {
            Timber.e("PostRemoteMediator: $e")
            return MediatorResult.Error(e)
        }
    }
}