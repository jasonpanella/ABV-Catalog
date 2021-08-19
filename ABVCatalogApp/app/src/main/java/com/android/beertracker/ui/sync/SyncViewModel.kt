package com.android.beertracker.ui.sync

import android.app.Application
import androidx.lifecycle.*
import com.android.beertracker.MyApp
import com.android.beertracker.R
import com.android.beertracker.database.BeerDatabaseDao
import com.android.beertracker.database.BeerEntity
import com.android.beertracker.readCSV
import kotlinx.coroutines.launch
import java.io.InputStream


class SyncViewModel(val database: BeerDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    private var tonight = MutableLiveData<BeerEntity?>()

    private val beers = database.getAllBeers()

    /**
     * If tonight has not been set, then the START button should be visible.
     */
    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }

    /**
     * If tonight has been set, then the STOP button should be visible.
     */
    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }

    /**
     * If there are any nights in the database, show the CLEAR button.
     */
    val clearButtonVisible = Transformations.map(beers) {
        it?.isNotEmpty()
    }

    /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    /**
     * Call this immediately after calling `show()` on a toast.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    /**
     * Variable that tells the Fragment to navigate to a specific [SleepQualityFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepQuality = MutableLiveData<BeerEntity>()

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getTonightFromDatabase(): BeerEntity? {
        var night = database.getTonight()
        //if (night?.endTimeMilli != night?.startTimeMilli) {
        //    night = null
        //}
        return night
    }

    private suspend fun insert(night: BeerEntity) {
        database.insert(night)
    }

    private suspend fun update(night: BeerEntity) {
        database.update(night)
    }

    private suspend fun clear() {
        database.clear()
    }


    /**
     * Executes when the START button is clicked.
     */
    fun onStartTracking() {

        viewModelScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            var beers = ArrayList<BeerEntity>()

            // create imput stream
            val inputStream: InputStream = MyApp.getInstance().resources.openRawResource(R.raw.beers)

            beers = readCSV(inputStream)

            // Insert the new beer list
            for (beer in beers) {
                insert(beer)
            }

            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     * Executes when the STOP button is clicked.
     */
    fun onStopTracking() {
        viewModelScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            //oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)

            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
        }
    }

    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            clear()

            // And clear tonight since it's no longer in the database
            tonight.value = null

            // Show a snackbar message, because it's friendly.
            _showSnackbarEvent.value = true
        }
    }
}