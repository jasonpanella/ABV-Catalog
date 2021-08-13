package com.example.abvcatalogapp.feature.launcher.presentation

import android.app.Activity
import android.os.Bundle
import com.example.abvcatalogapp.R
import com.example.abvcatalogapp.ui.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LauncherFragment: BaseFragment<LauncherViewModel>(R.layout.fragment_launcher) {

    override val viewModel: LauncherViewModel
        get() = getViewModel()

    override val fragmentSimpleName: String
        get() = LauncherFragment::class.java.simpleName


    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.init(activity as Activity)
    }



}