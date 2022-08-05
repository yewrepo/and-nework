package ru.netology.nework.app.ui.auth

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.netology.nework.R
import ru.netology.nework.app.*
import ru.netology.nework.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private var binding: FragmentAuthBinding? = null

    private val authViewModel: AuthViewModel by sharedViewModel()

    private val loginWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            authViewModel.onLoginChange(s)
        }
    }
    private val passWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            authViewModel.onPasswordChange(s)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false).also {
            it.loginText.addTextChangedListener(loginWatcher)
            it.passwordText.addTextChangedListener(passWatcher)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()

        authViewModel.authUiState.observe(viewLifecycleOwner) { state ->
            binding?.apply {
                loginButton.isEnabled = state.loginOk && state.passwordOk
                passwordLayout.error =
                    if (state.passwordOk) null
                    else getString(R.string.error_password)
            }
        }
        authViewModel.authStateResult.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Success -> {
                    hideLoading()
                    result.data?.apply {
                        goToHome()
                    }
                }
                Loading -> showLoading()
                Error -> showError(result.t!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        authViewModel.checkToken()
    }

    private fun showError(t: Throwable) {
        hideLoading()
        Toasty.error(requireContext(), t.message.orEmpty(), Toast.LENGTH_SHORT, true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showLoading() {
        binding?.apply {
            progress.show()
            loginLayout.isEnabled = false
            passwordLayout.isEnabled = false
            loginButton.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding?.apply {
            progress.hide()
            loginLayout.isEnabled = true
            passwordLayout.isEnabled = true
            loginButton.isEnabled = true
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_authFragment_to_registerFragment)
    }

    private fun initButtons() {
        binding?.apply {
            registrationHint.paintFlags = registrationHint.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            registrationHint.setOnClickListener {
                goToRegister()
            }
            loginButton.setOnClickListener {
                authViewModel.auth()
            }
        }
    }
}