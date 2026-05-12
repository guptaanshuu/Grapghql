package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class FuelingViewModel  @Inject constructor(
    private val repository: FuelQueryRepository,
    private val subscriptionManager: AppSyncSubscriptionManager,
) : ViewModel() {


    init {
        Timber.d("FuelingViewModel initialized")
        loadInitialFuelData()
        observeRealtimeUpdates()

        viewModelScope.launch {
            subscriptionManager.subscriptionEvents.collect { event ->
                Timber.tag("FuelingViewModel").d("New event to process: $event")
                // Parse the JSON and update your screen state here
            }
        }
    }

     fun loadInitialFuelData() {
        viewModelScope.launch {
            Timber.d("FuelingViewModel initialized")
            repository.executeFuelQuery()
        }
    }

     fun observeRealtimeUpdates() {
        subscriptionManager.connect()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionManager.disconnect()
    }
}

   /* private val _fuelList = MutableStateFlow<List<GetFuelsQuery.GetFuel>>(emptyList())


    val fuelList = _fuelList.asStateFlow()

    init {
        loadInitialData()
        observeRealtimeUpdates()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val fuels = repository.getFuels()
            _fuelList.value = fuels
        }
    }

    private fun observeRealtimeUpdates() {
        viewModelScope.launch {
            repository.observeFuelUpdates().collect { updatedFuel ->

                _fuelList.update { currentList ->
                    currentList.map {
                        if (it.id == updatedFuel.id) {
                            it.copy(price = updatedFuel.price)
                        } else it
                    }
                }
            }
        }
    }*/
