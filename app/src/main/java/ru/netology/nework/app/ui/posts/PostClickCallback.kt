package ru.netology.nework.app.ui.posts

import ru.netology.nework.app.model.PostActionType

interface PostClickCallback {
    fun onClick(position: Int, type: PostActionType)
}