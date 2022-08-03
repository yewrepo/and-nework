package ru.netology.nework.app.ui.loading

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.app.getInflater
import ru.netology.nework.databinding.ViewHolderLoadingStateBinding

class LoadingStateViewHolder(
    private val binding: ViewHolderLoadingStateBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            progress.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            error.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        }
    }

    companion object {

        @JvmStatic
        fun create(parent: ViewGroup, retryCallback: () -> Unit): LoadingStateViewHolder {
            val binding = ViewHolderLoadingStateBinding.inflate(parent.getInflater(), parent, false)
            return LoadingStateViewHolder(binding, retryCallback)
        }
    }
}
