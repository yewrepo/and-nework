package ru.netology.nework.app.ui.author

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.domain.WallDataRepository
import ru.netology.nework.model.post.Post

class AuthorWallViewModel(
    private val repository: WallDataRepository
) : ViewModel() {

    fun getData(authorId: Long): Flow<PagingData<Post>> {
        return repository.getAuthorWall(authorId)
    }
}