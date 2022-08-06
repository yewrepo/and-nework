package ru.netology.nework.app.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.netology.nework.R
import ru.netology.nework.app.ui.author.AuthorCardFragment.Companion.userData
import ru.netology.nework.app.ui.fetchUserData
import ru.netology.nework.app.ui.loading.LoadingStateAdapter
import ru.netology.nework.databinding.FragmentPostsBinding

class PostsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var recyclerManager: LinearLayoutManager? = null
    private var adapter: PostPagingDataAdapter? = null
    private var binding: FragmentPostsBinding? = null

    private val postsViewModel: PostsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostPagingDataAdapter(callback = object : PostClickCallback {
            override fun onOpenClick(position: Int) {
                adapter?.let {
                    it.snapshot()[position]?.apply {

                    }
                }
            }

            override fun onAuthorOpenClick(position: Int) {
                adapter?.let {
                    it.snapshot()[position]?.apply {
                        findNavController()
                            .navigate(
                                R.id.action_postsFragment_to_authorCardFragment,
                                Bundle().also { bundle ->
                                    bundle.userData = this.fetchUserData()
                                })
                    }
                }
            }

            override fun onLikeClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onRemoveClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onEditClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onYoutubeLinkClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onPhotoOpenClick(position: Int) {
                TODO("Not yet implemented")
            }

        })

        val concatAdapter = adapter?.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter?.retry() },
            footer = LoadingStateAdapter { adapter?.retry() }
        )

        recyclerManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        adapter?.addLoadStateListener { state ->
            binding?.apply {

                val isRefreshing = state.refresh is LoadState.Loading
                val isRefreshingError = state.refresh is LoadState.Error
                val isEmpty = adapter?.itemCount == 0
                val isForced = isEmpty || (postsViewModel.updateRequest.value == true)

                binding?.swiper?.isRefreshing = isRefreshing

                if (isRefreshingError) {
                    Toasty
                        .error(
                            requireContext(),
                            R.string.net_error_timeout,
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                }
            }
        }

        binding?.apply {
            swiper.setOnRefreshListener(this@PostsFragment)
            recycler.layoutManager = recyclerManager
            recycler.adapter = concatAdapter
        }

        lifecycleScope.launchWhenCreated {
            postsViewModel.data
                .collectLatest { data ->
                    adapter?.submitData(data)
                }
        }

        postsViewModel.updateRequest.observe(viewLifecycleOwner) {
            if (it == true) {
                adapter?.refresh()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onRefresh() {
        postsViewModel.requestRefreshing()
    }
}