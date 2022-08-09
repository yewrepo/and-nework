package ru.netology.nework.app.model

import android.os.Bundle

data class PostActionRequest(
    val bundle: Bundle,
    val postId: Long,
    val ownedByMe: Boolean,
    val actionRequest: PostActionType
)
