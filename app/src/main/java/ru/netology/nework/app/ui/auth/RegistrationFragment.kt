package ru.netology.nework.app.ui.auth

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
import ru.netology.nework.app.Error
import ru.netology.nework.app.Loading
import ru.netology.nework.app.SimpleTextWatcher
import ru.netology.nework.app.Success
import ru.netology.nework.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null

    private val regViewModel: RegistrationViewModel by sharedViewModel()

    private val loginWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            regViewModel.onLoginChange(s)
        }
    }

    private val nameWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            regViewModel.onNameChange(s)
        }
    }

    private val passWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            regViewModel.onPasswordChange(s)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false).also {
            it.loginText.addTextChangedListener(loginWatcher)
            it.passwordText.addTextChangedListener(passWatcher)
            it.nameText.addTextChangedListener(nameWatcher)
            it.registrationButton.setOnClickListener {
                regViewModel.register()
            }
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regViewModel.regState.observe(viewLifecycleOwner) { state ->
            binding?.apply {
                registrationButton.isEnabled = state.nameOk && state.loginOk && state.passwordOk
                loginLayout.error =
                    if (state.loginOk) null
                    else getString(R.string.error_login)
                passwordLayout.error =
                    if (state.passwordOk) null
                    else getString(R.string.error_password)
                nameLayout.error =
                    if (state.nameOk) null
                    else getString(R.string.error_name)
            }
        }
        regViewModel.regStateResult.observe(viewLifecycleOwner) { result ->
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
            registrationButton.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding?.apply {
            progress.hide()
            loginLayout.isEnabled = true
            passwordLayout.isEnabled = true
            registrationButton.isEnabled = true
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }
}