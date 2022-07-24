package ru.netology.nework.app.ui.auth

import android.text.Editable
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.nework.app.Resource
import ru.netology.nework.app.getOrThrow
import ru.netology.nework.data.TokenMapper
import ru.netology.nework.data.network.TokenApi
import ru.netology.nework.data.remote.AuthRequestData
import ru.netology.nework.domain.TokenDataSource
import ru.netology.nework.model.AuthState
import ru.netology.nework.model.user.Token

class AuthViewModel(
    private val tokenDataSource: TokenDataSource,
    private val tokenApi: TokenApi
) : ViewModel() {

    private val _authState: MutableLiveData<AuthState> = MutableLiveData(AuthState())
    val authUiState: LiveData<AuthState>
        get() = _authState

    private val _authStateResult: MutableLiveData<Resource<Token>> =
        MutableLiveData()
    val authStateResult: LiveData<Resource<Token>>
        get() = _authStateResult

    fun onLoginChange(s: Editable?) {
        _authState.value?.apply {
            val text = s.toString()
            _authState.postValue(copy(loginOk = text.isNotEmpty(), login = text))
        }
    }

    fun onPasswordChange(s: Editable?) {
        val pass = s.toString()
        _authState.value?.apply {
            _authState.postValue(copy(passwordOk = pass.isNotEmpty(), password = pass))
        }
    }

    fun auth() {
        _authStateResult.postValue(Resource.loading())
        _authState.value?.apply {
            viewModelScope.launch {
                try {
                    val result =
                        tokenApi.authentication(AuthRequestData(login, password)).getOrThrow().let {
                            return@let TokenMapper().transform(it)
                        }
                    tokenDataSource.setToken(result)
                    _authStateResult.postValue(Resource.success(result))
                } catch (e: Exception) {
                    _authStateResult.postValue(Resource.error(e))
                }
            }
        }
    }
}