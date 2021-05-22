package ru.startandroid.develop.twichapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.twichapptest.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = binding.toolbar

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }
}