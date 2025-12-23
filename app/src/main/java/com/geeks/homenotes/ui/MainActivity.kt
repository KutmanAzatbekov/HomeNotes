package com.geeks.homenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.geeks.homenotes.R
import com.geeks.homenotes.data.local.Pref
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var pref: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        pref = Pref(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_host)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val isOnboard = pref.isOnBoardingShown()

        when{
            !isOnboard -> {
                navGraph.setStartDestination(R.id.onBoardFragment)
            }
            currentUser == null ->{
                navGraph.setStartDestination(R.id.authFragment)
            }
            else->{
                navGraph.setStartDestination(R.id.mainFragment)
            }
        }

        navController.graph = navGraph

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}