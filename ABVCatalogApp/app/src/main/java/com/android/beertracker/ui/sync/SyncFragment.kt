package com.android.beertracker.ui.sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.beertracker.R
import com.android.beertracker.database.BeerDatabase
import com.android.beertracker.databinding.FragmentGalleryBinding
import com.google.android.material.snackbar.Snackbar

class SyncFragment : Fragment() {

    private lateinit var syncViewModel: SyncViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val application = requireNotNull(this.activity).application

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentGalleryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_gallery, container, false)

        // Create an instance of the ViewModel Factory.
        val dataSource = BeerDatabase.getInstance(application).beerDatabaseDao
        val viewModelFactory = SyncViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        syncViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SyncViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.syncViewModel = syncViewModel

//        val textView: TextView = binding.root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)

        // Add an Observer on the state variable for showing a Snackbar message
        // when the CLEAR button is pressed.
        syncViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                syncViewModel.doneShowingSnackbar()
            }
        })


        return binding.root
    }
}