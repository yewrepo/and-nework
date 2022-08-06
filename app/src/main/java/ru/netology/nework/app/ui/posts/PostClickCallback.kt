package ru.netology.nework.app.ui.posts

interface PostClickCallback {
    fun onOpenClick(position: Int)
    fun onAuthorOpenClick(position: Int)
    fun onLikeClick(position: Int)
    fun onRemoveClick(position: Int)
    fun onEditClick(position: Int)
    fun onYoutubeLinkClick(position: Int)
    fun onPhotoOpenClick(position: Int)
}