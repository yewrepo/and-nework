package ru.netology.nework.domain

interface Mapper<Source, destination> {
    fun transform(data: Source): destination
}
