package ru.netology.nework.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.netology.nework.app.ui.auth.AuthViewModel
import ru.netology.nework.data.local.TokenDataSourceImpl
import ru.netology.nework.data.network.ApiClient
import ru.netology.nework.domain.TokenDataSource

val appModule = module {

    single { TokenDataSourceImpl(get()) as TokenDataSource }
    single { ApiClient(get()).neWorkApi() }
    single { ApiClient(get()).tokenApi() }

    viewModel {
        AuthViewModel(
            app = get(),
            tokenApi = get()
        )
    }
}

val allModules = appModule
