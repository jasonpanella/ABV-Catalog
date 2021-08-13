package com.example.abvcatalogapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.common.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val navigation: SingleLiveEvent<NavigationParameters> by lazy {
        SingleLiveEvent<NavigationParameters>()
    }

    protected fun navigateToAction(action: NavDirections, afterDelay: Int? = null) {
        navigation.postValue(NavigationParameters(action, afterDelay))
    }
}


data class NavigationParameters(
        val action: NavDirections,
        val navigationDelay: Int?
)