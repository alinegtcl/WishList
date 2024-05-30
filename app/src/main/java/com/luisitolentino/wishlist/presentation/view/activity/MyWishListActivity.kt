package com.luisitolentino.wishlist.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.luisitolentino.wishlist.R
import com.luisitolentino.wishlist.databinding.ActivityMyWishListBinding

class MyWishListActivity : AppCompatActivity() {
    private val binding: ActivityMyWishListBinding by lazy {
        ActivityMyWishListBinding.inflate(layoutInflater)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarWishListManager)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerWishListManager) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerWishListManager)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}