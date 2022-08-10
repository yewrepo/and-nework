package ru.netology.nework.app.ui.author

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nework.app.Resource
import ru.netology.nework.app.ui.SingleLiveEvent
import ru.netology.nework.domain.WallDataRepository
import ru.netology.nework.model.post.Post
import timber.log.Timber

class AuthorWallViewModel(
    private val repository: WallDataRepository
) : ViewModel() {

    private val _postList = MutableLiveData<Resource<List<Post>>>(Resource.loading())
    val postList: LiveData<Resource<List<Post>>>
        get() = _postList

    fun refresh(authorId: Long) {
        viewModelScope.launch {
            try {
                val list = repository.getAuthorWall(authorId)
                _postList.postValue(Resource.success(list))
            } catch (e: Exception) {
                Timber.e("error:$e")
                _postList.postValue(Resource.error(e))
            }
        }
    }
}