package ru.netology.nework.app.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nework.R
import ru.netology.nework.app.getContext
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
            callback.onOpenClick(bindingAdapterPosition)
        }
        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { item ->
                    return@setOnMenuItemClickListener when (item.itemId) {
                        R.id.remove -> {
                            callback.onRemoveClick(bindingAdapterPosition)
                            true
                        }
                        R.id.edit -> {
                            callback.onEditClick(bindingAdapterPosition)
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
            binding.author.text = this.author
            binding.published.text = this.published.toCommon()
            binding.content.text = this.content
        }
    }

    private fun loadAvatar(post: Post) {
        Glide.with(getContext())
            .load(post.authorAvatar)
            .timeout(10_000)
            .placeholder(R.drawable.ic_broken_image_24dp)
            .centerCrop()
            .into(binding.avatar)
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
