package com.lyvetech.transnature.features

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnboardingUtils {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        manageBottomNavigation()
        setContentView(binding.root)
    }

    override fun showProgressBar() {
        TODO("Not yet implemented")
    }

    override fun hideProgressBar() {
        TODO("Not yet implemented")
    }

    override fun showTopAppBar(title: String) {
        TODO("Not yet implemented")
    }

    override fun hideTopAppBar() {
        TODO("Not yet implemented")
    }

    override fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun hideBottomNav() {
        TODO("Not yet implemented")
    }

    private fun manageBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.findNavController())
    }
}