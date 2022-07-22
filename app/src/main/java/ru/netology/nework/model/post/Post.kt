package ru.netology.nework.model.post

import ru.netology.nework.model.Coordinates
import ru.netology.nework.model.attachment.Attachment
import java.time.LocalDateTime

data class Post(
    val id: String,
    val authorId: String,
    val authorAvatar: String,
    val content: String,
    val published: LocalDateTime,
    val coords: Coordinates?,
    val link: String,
    val likeOwnerIds: List<Int>,
    val mentionIds: List<Int>,
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val attachment: Attachment?,
    val ownedByMe: Boolean
)
