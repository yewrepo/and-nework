package ru.netology.nework.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nework.model.Coordinates
import ru.netology.nework.model.attachment.Attachment
import java.time.LocalDateTime

@Entity
data class PostEntity(
    @PrimaryKey
    val id: Long,
    val authorId: Long,
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
