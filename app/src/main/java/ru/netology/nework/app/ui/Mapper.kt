package ru.netology.nework.app.ui

import ru.netology.nework.app.ui.author.model.UserData
import ru.netology.nework.model.post.Post

fun Post.fetchUserData() = UserData(
    authorId = authorId,
    author = author,
    authorAvatar = authorAvatar
)
