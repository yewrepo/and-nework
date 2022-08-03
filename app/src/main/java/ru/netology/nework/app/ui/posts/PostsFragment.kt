package ru.netology.nework.app.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
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

        adapter = PostPagingDataAdapter(callback = { position ->
            adapter?.let {
                it.snapshot()[position]?.apply {

                }
            }
        })

        val concatAdapter = adapter?.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter?.retry() },
            footer = LoadingStateAdapter { adapter?.retry() }
        )

        recyclerManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        binding?.apply {
            swiper.setOnRefreshListener(this@PostsFragment)
            recycler.layoutManager = recyclerManager
            recycler.adapter = concatAdapter
        }

        lifecycleScope.launchWhenCreated {
            postsViewModel.data.collectLatest {
                adapter?.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onRefresh() {
        adapter?.refresh()
    }
}