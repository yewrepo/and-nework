package ru.netology.nework.app.di

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.netology.nework.app.ui.auth.AuthViewModel
import ru.netology.nework.app.ui.auth.RegistrationViewModel
import ru.netology.nework.app.ui.author.AuthorWallViewModel
import ru.netology.nework.app.ui.posts.PostsViewModel
import ru.netology.nework.data.PostDataRepositoryImpl
import ru.netology.nework.data.db.AppDb
import ru.netology.nework.data.local.RoomPostDataSourceImpl
import ru.netology.nework.data.local.token.TokenDataSourceImpl
import ru.netology.nework.data.network.ApiClient
import ru.netology.nework.data.remote.post.PostRemoteMediator
import ru.netology.nework.data.remote.post.RetrofitPostDataSourceImpl
import ru.netology.nework.domain.*

@Suppress("USELESS_CAST")
val appModule = module {

    single { TokenDataSourceImpl(get()) as TokenDataSource }
    single { ApiClient(get()).neWorkApi() }
    single { ApiClient(get()).tokenApi() }
    single {
        Room.databaseBuilder(androidContext(), AppDb::class.java, "app.db")
            .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
            .fallbackToDestructiveMigrationOnDowngrade()
            .allowMainThreadQueries()
            .build() as AppDb
    }
    single {
        val database = Room.databaseBuilder(androidContext(), AppDb::class.java, "app.db")
            .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
            .fallbackToDestructiveMigrationOnDowngrade()
            .allowMainThreadQueries()
            .build() as AppDb
        database.postDao()
    }

    factory { PostRemoteMediator(get(), get()) }
    single { RoomPostDataSourceImpl(get()) as PostDataLocalSource }
    factory { RetrofitPostDataSourceImpl(get()) as PostDataRemoteSource }
    single { PostDataRepositoryImpl(get(), get(), get()) as PostDataRepository }

    viewModel {
        AuthViewModel(
            tokenDataSource = get(),
            tokenApi = get()
        )
    }
    viewModel {
        RegistrationViewModel(
            tokenDataSource = get(),
            tokenApi = get()
        )
    }
    viewModel {
        PostsViewModel(
            repository = get()
        )
    }
    viewModel {
        AuthorWallViewModel(
            repository = get()
        )
    }
}

val allModules = appModule
