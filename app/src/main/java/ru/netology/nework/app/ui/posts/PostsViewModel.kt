package ru.netology.nework.app.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nework.app.ui.SingleLiveEvent
import ru.netology.nework.domain.PostDataRepository

class PostsViewModel(
    private val repository: PostDataRepository
) : ViewModel() {

    private val _updateRequest: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val updateRequest: LiveData<Boolean>
        get() = _updateRequest

    val data = repository.data

    fun requestRefreshing() {
        _updateRequest.postValue(true)
    }
}