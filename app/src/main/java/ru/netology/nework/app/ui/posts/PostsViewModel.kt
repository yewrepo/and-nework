package ru.netology.nework.app.ui.posts

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nework.app.model.PostActionRequest
import ru.netology.nework.app.model.PostActionType
import ru.netology.nework.app.ui.SingleLiveEvent
import ru.netology.nework.app.ui.author.AuthorCardFragment.Companion.userData
import ru.netology.nework.app.ui.fetchCoordinatesData
import ru.netology.nework.app.ui.fetchUserData
import ru.netology.nework.app.ui.map.MapFragment.Companion.coordinatesData
import ru.netology.nework.domain.PostDataRepository
import ru.netology.nework.model.post.Post

class PostsViewModel(
    private val repository: PostDataRepository
) : ViewModel() {

    private val _updateRequest: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val updateRequest: LiveData<Boolean>
        get() = _updateRequest

    private val _postActionRequest: SingleLiveEvent<PostActionRequest> = SingleLiveEvent()
    val postActionRequest: LiveData<PostActionRequest>
        get() = _postActionRequest

    val data = repository.data

    fun requestRefreshing() {
        _updateRequest.postValue(true)
    }

    fun openRequest(post: Post, type: PostActionType) {
        val bundle = when (type) {
            PostActionType.OPEN -> Bundle()
            PostActionType.AUTHOR_WALL -> Bundle().also { bundle ->
                bundle.userData = post.fetchUserData()
            }
            PostActionType.OPEN_MAP -> Bundle().also { bundle ->
                bundle.coordinatesData = post.fetchCoordinatesData()
            }
            PostActionType.LIKE -> Bundle()
            PostActionType.REMOVE -> Bundle()
            PostActionType.EDIT -> Bundle()
        }
        _postActionRequest.postValue(
            PostActionRequest(
                bundle = bundle,
                postId = post.id,
                ownedByMe = post.likedByMe,
                actionRequest = type
            )
        )
    }

    fun like(postId: Long, ownedByMe: Boolean) {
        viewModelScope.launch {
            if (ownedByMe) {
                repository.unlikePost(postId)
            } else {
                repository.likePost(postId)
            }
        }
    }
}