package com.example.reviewapp.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.navigation.R.id
import com.example.navigation.R.navigation
import com.example.presentation.utils.observeEvent
import com.example.reviewapp.R
import com.example.reviewapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)


        val navController = getNavController()
        viewModel.launchMainScreenEvent.observeEvent(this){
            prepareRootNavController(it, navController)
        }

        viewModel.username.observe(this) {
            binding.usernameTextView.text = it
        }

        binding.exitButton.setOnClickListener {
            viewModel.logout()
        }

        observeRestartAppFromLoginScreenEvent()
    }

    private fun prepareRootNavController(isSignedIn: Boolean, navController: NavController) {
        val graph = navController.navInflater.inflate(getNavigationGraphId())
        graph.setStartDestination(
            if (isSignedIn) {
                getCatalogFilmsDestination()
            } else {
                getSignInDestination()
            }
        )
        navController.graph = graph
    }

    private fun getNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun getNavigationGraphId(): Int = navigation.nav_graph

    private fun getSignInDestination(): Int = id.signInFragment

    private fun getCatalogFilmsDestination(): Int = id.catalogFilmsFragment

    private fun observeRestartAppFromLoginScreenEvent() {
        viewModel.restartWithSignInEvent.observeEvent(this) {
            getNavController().navigate(id.signInFragment, null, navOptions {
                popUpTo(id.catalogFilmsFragment) {
                    inclusive = true
                }
            })
        }
    }

}