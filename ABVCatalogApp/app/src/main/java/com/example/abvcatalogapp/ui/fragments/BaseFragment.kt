package com.example.abvcatalogapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.abvcatalogapp.viewModel.BaseViewModel

abstract class BaseFragment<TViewModel : BaseViewModel>(layoutResource: Int) : Fragment(layoutResource) {

    abstract val viewModel: TViewModel
    abstract val fragmentSimpleName: String

    //region Fragment Lifecycle
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}