package ru.netology.nework.data.remote

import ru.netology.nework.model.Coordinates
import java.time.LocalDateTime

data class PostRemote(
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: LocalDateTime,
    val coords: Coordinates?,
    val link: String?,
    val likeOwnerIds: List<Int>,
    val mentionIds: List<Int>,
    val mentionedMe: Boolean,
    val likedByMe: Boolean, // не работает
    val attachment: AttachmentRemote?,
    val ownedByMe: Boolean
)
