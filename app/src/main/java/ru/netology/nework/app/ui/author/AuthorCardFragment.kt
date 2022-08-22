package ru.netology.nework.app.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.netology.nework.R
import ru.netology.nework.app.Error
import ru.netology.nework.app.Loading
import ru.netology.nework.app.Success
import ru.netology.nework.app.loadUrl
import ru.netology.nework.app.model.PostActionType
import ru.netology.nework.app.model.UserData
import ru.netology.nework.app.model.UserDataArg
import ru.netology.nework.app.ui.posts.PostClickCallback
import ru.netology.nework.app.ui.posts.PostDataAdapter
import ru.netology.nework.app.ui.posts.PostsViewModel
import ru.netology.nework.databinding.FragmentAuthorCardBinding
import ru.netology.nework.model.post.Post

class AuthorCardFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var adapter: PostDataAdapter? = null
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

        adapter = PostDataAdapter(callback = object : PostClickCallback {
            override fun onClick(position: Int, type: PostActionType) {
                adapter?.let {
                    it.currentList[position]?.apply {
                        postsViewModel.openRequest(this, type)
                    }
                }
            }
        })


        val recyclerManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        binding?.apply {
            swiper.setOnRefreshListener(this@AuthorCardFragment)
            recycler.layoutManager = recyclerManager
            recycler.adapter = adapter
        }

        authorWallViewModel.postList.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Error -> showError(resource.t!!)
                Loading -> showLoading()
                Success -> showData(resource.data!!)
            }
        }
        authorWallViewModel.refresh(authorId = userData!!.authorId)

        postsViewModel.postActionRequest.observe(viewLifecycleOwner) {
            when (it.actionRequest) {
                PostActionType.OPEN -> TODO()
                PostActionType.LIKE -> {
                    postsViewModel.like(it.postId, it.ownedByMe)
                }
                PostActionType.REMOVE -> TODO()
                PostActionType.EDIT -> TODO()
                PostActionType.OPEN_MAP -> {
                    findNavController()
                        .navigate(R.id.action_authorCardFragment_to_mapFragment, it.bundle)
                }
            }
        }
    }

    private fun showData(data: List<Post>) {
        adapter?.submitList(data)
        binding?.apply {
            swiper.isRefreshing = false
            val isEmpty = data.isEmpty()
            swiper.isVisible = isEmpty.not()
            emptyText.isVisible = isEmpty
        }
    }

    private fun showLoading() {
        binding?.apply {
            swiper.isRefreshing = true
        }
    }

    private fun showError(t: Throwable) {
        binding?.apply {
            swiper.isRefreshing = false
            Toasty.error(requireContext(), R.string.net_error_timeout).show()
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
        authorWallViewModel.refresh(authorId = userData!!.authorId)
    }

    companion object {
        var Bundle.userData: UserData? by UserDataArg
    }
}