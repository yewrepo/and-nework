package ru.netology.nework.app.ui

import ru.netology.nework.app.model.CoordinatesData
import ru.netology.nework.app.model.UserData
import ru.netology.nework.model.post.Post

fun Post.fetchUserData() = UserData(
    authorId = authorId,
    author = author,
    authorAvatar = authorAvatar
)

fun Post.fetchCoordinatesData() = CoordinatesData(
    lat = coords?.lat ?: 0.0,
    long = coords?.long ?: 0.0
)
