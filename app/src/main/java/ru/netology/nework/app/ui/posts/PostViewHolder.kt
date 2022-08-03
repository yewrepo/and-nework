package ru.netology.nework.app.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    fun bind(plan: Post?) {
        plan?.apply {

        }
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
