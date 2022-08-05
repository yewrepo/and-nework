package ru.netology.nework.app.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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
    callback: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    val context: Context = binding.root.context

    private val clickCallback = View.OnClickListener { callback(bindingAdapterPosition) }

    init {
        binding.root.setOnClickListener(clickCallback)
        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { item ->
                    return@setOnMenuItemClickListener when (item.itemId) {
                        R.id.remove -> {
                            true
                        }
                        R.id.edit -> {
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
            binding.author.text = this.author
            binding.published.text = this.published.toCommon()
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

    companion object {

        @JvmStatic
        fun create(parent: ViewGroup, callback: (position: Int) -> Unit): PostViewHolder {
            val binding =
                ViewHolderPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding, callback)
        }
    }
}
