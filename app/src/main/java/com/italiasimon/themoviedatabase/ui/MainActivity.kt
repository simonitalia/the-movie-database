package com.italiasimon.themoviedatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        drawerLayout = binding.drawerLayout

        // Custom Action Bar Toolbar
        setUpToolbar()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        appDrawerListener()

        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun appDrawerListener() {
        navController.addOnDestinationChangedListener { nc, nd, _ : Bundle? ->

            if (nd.id == nc.graph.startDestinationId) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    private fun setUpToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar.apply {
            this?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
            this?.setDisplayHomeAsUpEnabled(true)
        }
    }
}