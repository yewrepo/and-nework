package ru.netology.nework.app.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.netology.nework.app.loadUrl
import ru.netology.nework.app.model.PostActionType
import ru.netology.nework.app.model.UserData
import ru.netology.nework.app.model.UserDataArg
import ru.netology.nework.app.ui.loading.LoadingStateAdapter
import ru.netology.nework.app.ui.posts.PostClickCallback
import ru.netology.nework.app.ui.posts.PostPagingDataAdapter
import ru.netology.nework.app.ui.posts.PostsViewModel
import ru.netology.nework.databinding.FragmentAuthorCardBinding
import timber.log.Timber

class AuthorCardFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var adapter: PostPagingDataAdapter? = null
    private var userData: UserData? = null
    private var binding: FragmentAuthorCardBinding? = null

    private val authorWallViewModel: AuthorWallViewModel by sharedViewModel()
    private val postsViewModel: PostsViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = requireArguments().userData!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorCardBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUserData()

        adapter = PostPagingDataAdapter(callback = object : PostClickCallback {
            override fun onClick(position: Int, type: PostActionType) {
                adapter?.let {
                    it.snapshot()[position]?.apply {
                        postsViewModel.openRequest(this, type)
                    }
                }
            }
        })

        val concatAdapter = adapter?.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter?.retry() },
            footer = LoadingStateAdapter { adapter?.retry() }
        )

        val recyclerManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        binding?.apply {
            recycler.layoutManager = recyclerManager
            recycler.adapter = concatAdapter
        }

        adapter?.addLoadStateListener { state ->
            binding?.apply {
                val isRefreshing = state.refresh is LoadState.Loading
                val isEnd = state.refresh.endOfPaginationReached
                Timber.e("isRefreshing:$isRefreshing; isEnd: $isEnd")
            }
        }

        lifecycleScope.launchWhenCreated {
            authorWallViewModel.getData(authorId = userData!!.authorId)
                .collectLatest { data ->
                    adapter?.submitData(data)
                }
        }
    }

    private fun displayUserData() {
        binding?.apply {
            userData?.let {
                this.author.text = it.author
                this.avatar.loadUrl(it.authorAvatar)
            }
        }
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }

    companion object {
        var Bundle.userData: UserData? by UserDataArg
    }
}