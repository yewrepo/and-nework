package ru.netology.nework.app.ui.auth

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nework.app.Resource
import ru.netology.nework.data.network.TokenApi
import ru.netology.nework.model.AuthState
import ru.netology.nework.model.AuthStateResult

class AuthViewModel(
    app: Application,
    private val tokenApi: TokenApi
) : AndroidViewModel(app) {

    private val _authState: MutableLiveData<AuthState> = MutableLiveData(AuthState())
    val authUiState: LiveData<AuthState>
        get() = _authState

    private val _authStateResult: MutableLiveData<Resource<AuthStateResult>> =
        MutableLiveData()
    val authStateResult: LiveData<Resource<AuthStateResult>>
        get() = _authStateResult

    fun onPasswordChange(s: Editable?) {
        val pass = s.toString()
        _authState.value?.apply {
            _authState.postValue(copy(passwordOk = pass.isNotEmpty(), password = pass))
        }
    }

    fun auth() {
        _authStateResult.postValue(Resource.loading())
        _authState.value?.apply {

        }
    }
}