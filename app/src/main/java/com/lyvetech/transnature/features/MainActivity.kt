package com.lyvetech.transnature.features

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnboardingUtils {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        manageBottomNavigation()
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun showProgressBar() {
        binding.pb.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.pb.visibility = View.GONE
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
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun manageBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.findNavController())
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}