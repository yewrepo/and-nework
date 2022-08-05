package ru.netology.nework.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.windowInsetTypesOf
import ru.netology.nework.R
import ru.netology.nework.databinding.ActivityMainBinding

//https://netomedia.ru/swagger/

class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        Insetter.builder()
            .margin(windowInsetTypesOf(ime = true, statusBars = true))
            .applyToView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.authFragment)
            .setFallbackOnNavigateUpListener(::onSupportNavigateUp)
            .build()

        navController
            .addOnDestinationChangedListener { _, destination, _ ->
            val showAppBar = destination.id != R.id.authFragment
                    && destination.id != R.id.registerFragment
            binding.appBar.isVisible = showAppBar
            binding.navView.isVisible = showAppBar

        }

        setupActionBarWithNavController(navController, appBarConfiguration!!)
        binding.navView.setupWithNavController(navController)
    }
}