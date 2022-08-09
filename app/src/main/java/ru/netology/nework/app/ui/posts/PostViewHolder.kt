package ru.netology.nework.app.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.R
import ru.netology.nework.app.loadUrl
import ru.netology.nework.app.model.PostActionType
import ru.netology.nework.app.toCommon
import ru.netology.nework.databinding.ViewHolderPostBinding
import ru.netology.nework.model.post.Post

class PostViewHolder(
    private val binding: ViewHolderPostBinding,
    private val callback: PostClickCallback
) : RecyclerView.ViewHolder(binding.root) {

    val context: Context = binding.root.context

    init {
        binding.root.setOnClickListener {
            callback.onClick(bindingAdapterPosition, PostActionType.OPEN)
        }
        binding.avatar.setOnClickListener {
            callback.onClick(bindingAdapterPosition, PostActionType.AUTHOR_WALL)
        }
        binding.likes.setOnCheckedChangeListener { _, _ ->
            callback.onClick(bindingAdapterPosition, PostActionType.LIKE)
        }
        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { item ->
                    return@setOnMenuItemClickListener when (item.itemId) {
                        R.id.remove -> {
                            callback.onClick(bindingAdapterPosition, PostActionType.REMOVE)
                            true
                        }
                        R.id.edit -> {
                            callback.onClick(bindingAdapterPosition, PostActionType.EDIT)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }

    fun bind(plan: Post?) {
        plan?.apply {
            loadAvatar(this)
            setLikes(this)
            binding.attachmentLayout.setAttachment(this.attachment)
            binding.author.text = this.author
            binding.published.text = this.published.toCommon()
            binding.content.text = this.content
        }
    }

    private fun loadAvatar(post: Post) {
        binding.avatar.loadUrl(post.authorAvatar)
    }

    private fun setLikes(post: Post) {
        binding.likes.text = post.likeOwnerIds.size.toString()
        binding.likes.isChecked = post.likedByMe
    }

    companion object {

        @JvmStatic
        fun create(parent: ViewGroup, callback: PostClickCallback): PostViewHolder {
            val binding =
                ViewHolderPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding, callback)
        }
    }
}
