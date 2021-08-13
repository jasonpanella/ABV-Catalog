package com.example.abvcatalogapp.feature.sync.presentation

import android.os.Bundle
import com.example.abvcatalogapp.R
import com.example.abvcatalogapp.ui.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SyncFragment : BaseFragment<SyncViewModel>(R.layout.fragment_sync) {

    override val viewModel: SyncViewModel
        get() = getViewModel()

    override val fragmentSimpleName: String
        get() = "SyncFragment"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel.sync()
    }
}