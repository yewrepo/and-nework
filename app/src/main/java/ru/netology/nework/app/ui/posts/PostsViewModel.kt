package ru.netology.nework.app.ui.posts

import androidx.lifecycle.ViewModel
import ru.netology.nework.domain.PostDataRepository

class PostsViewModel(
    private val repository: PostDataRepository
) : ViewModel() {

    val data = repository.data
}