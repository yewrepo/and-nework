package ru.netology.nework.app.ui.auth

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nework.app.Resource
import ru.netology.nework.app.getOrThrow
import ru.netology.nework.data.TokenMapper
import ru.netology.nework.data.network.TokenApi
import ru.netology.nework.domain.TokenDataSource
import ru.netology.nework.model.RegistrationState
import ru.netology.nework.model.user.Token

class RegistrationViewModel(
    private val tokenDataSource: TokenDataSource,
    private val tokenApi: TokenApi
) : ViewModel() {

    private val _regState: MutableLiveData<RegistrationState> = MutableLiveData(RegistrationState())
    val regState: LiveData<RegistrationState>
        get() = _regState

    private val _regStateResult: MutableLiveData<Resource<Token>> =
        MutableLiveData()
    val regStateResult: LiveData<Resource<Token>>
        get() = _regStateResult

    fun onLoginChange(s: Editable?) {
        val login = s.toString()
        _regState.value?.apply {
            _regState.postValue(copy(loginOk = login.isNotEmpty(), login = login))
        }
    }

    fun onNameChange(s: Editable?) {
        val name = s.toString()
        _regState.value?.apply {
            _regState.postValue(copy(nameOk = name.isNotEmpty(), name = name))
        }
    }

    fun onPasswordChange(s: Editable?) {
        val pass = s.toString()
        _regState.value?.apply {
            _regState.postValue(copy(passwordOk = pass.isNotEmpty(), password = pass))
        }
    }

    fun register() {
        _regStateResult.postValue(Resource.loading())
        _regState.value?.apply {
            viewModelScope.launch {
                try {
                    val result =
                        tokenApi.registration(
                            login = login,
                            password = password,
                            name = name
                        ).getOrThrow().let {
                            return@let TokenMapper().transform(it)
                        }
                    tokenDataSource.setToken(result)
                    _regStateResult.postValue(Resource.success(result))
                } catch (e: Exception) {
                    _regStateResult.postValue(Resource.error(e))
                }
            }
        }
    }
}