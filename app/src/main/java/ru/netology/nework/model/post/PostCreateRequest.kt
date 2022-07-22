package ru.netology.nework.model.post

import ru.netology.nework.model.Coordinates
import ru.netology.nework.model.attachment.Attachment

data class PostCreateRequest(
    val id: String,
    val content: String,
    val coords: Coordinates,
    val link: String,
    val mentionIds: List<Int>,
    val attachment: Attachment,
)
