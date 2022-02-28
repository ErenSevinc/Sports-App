package com.example.sportsbettingapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.sportsbettingapp.R
import com.example.sportsbettingapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAppBars()
    }

    private fun setupAppBars() {
        val controller = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.leagueFragment,
                R.id.upcomingMatchFragment,
                R.id.couponFragment
            )
        )
        binding.toolbar.setupWithNavController(controller, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(controller)
        viewModel.title.observe(this) {
            title = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}




