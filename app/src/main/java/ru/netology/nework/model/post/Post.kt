package ru.netology.nework.model.post

import ru.netology.nework.model.Coordinates
import ru.netology.nework.model.attachment.Attachment
import java.time.LocalDateTime

data class Post(
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: LocalDateTime,
    val coords: Coordinates?,
    val link: String?,
    val likeOwnerIds: List<Int>,
    val mentionIds: List<Int>,
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val attachment: Attachment?,
    val ownedByMe: Boolean,
    val isNew: Boolean
)

fun Post.hasCoordinates(): Boolean {
    return coords != null && coords.lat != 0.0 && coords.long != 0.0
}