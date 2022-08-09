package ru.netology.nework.app.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.netology.nework.app.loadUrl
import ru.netology.nework.app.model.UserData
import ru.netology.nework.app.model.UserDataArg
import ru.netology.nework.databinding.FragmentAuthorCardBinding

class AuthorCardFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var userData: UserData? = null
    private var binding: FragmentAuthorCardBinding? = null

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