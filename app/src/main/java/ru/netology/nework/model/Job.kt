package ru.netology.nework.model

import java.time.LocalDateTime

data class Job(
    val id: Int,
    val name: String,
    val position: String,
    val start: LocalDateTime,
    val finish: LocalDateTime,
    val link: String,
)
