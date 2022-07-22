package ru.netology.nework

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import ru.netology.nework.app.di.allModules

class NeWorkApp : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NeWorkApp)
            modules(allModules)
        }

        MultiDex.install(this)
    }
}