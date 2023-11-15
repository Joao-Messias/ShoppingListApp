package com.example.shoppinglistapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.shoppinglistapp.data.NetworkChangeReceiver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_lists, R.id.navigation_new_list
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Inicializa o BroadcastReceiver
        networkChangeReceiver = NetworkChangeReceiver {
            // Se a rede estiver disponível, sincroniza os dados com o Firebase
        }
    }

    override fun onStart() {
        super.onStart()
        // Registra o BroadcastReceiver para monitorar mudanças na conectividade de rede
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        // Desregistra o BroadcastReceiver
        unregisterReceiver(networkChangeReceiver)
    }
}
