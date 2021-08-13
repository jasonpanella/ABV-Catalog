package com.example.abvcatalogapp.feature.launcher.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.example.abvcatalogapp.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class LauncherViewModel: BaseViewModel() {

    private val minimumShowTimeMilliSeconds = 1_000

    fun init(activity: Activity) {
        viewModelScope.launch {
            //visitManager.sanitiseVisits()
//            if (!loginManager.hasSession()) {
//                navigateToAction(
//                    LauncherFragmentDirections.actionLauncherFragmentToLoginFragment(),
//                    minimumShowTimeMilliSeconds
//                )
//            } else {
//                attemptLogin(activity)
//            }
            //navigateToVisitListFragment()
//            navigateToAction(
//                LauncherFragmentDirections.actionLauncherFragmentToLoginFragment(),
//                minimumShowTimeMilliSeconds
//            )
        }
    }

}