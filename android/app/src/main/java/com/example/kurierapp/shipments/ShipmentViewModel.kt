package com.example.kurierapp.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurierapp.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShipmentViewModel: ViewModel() {
    private val api = NetworkClient.api
    val _state = MutableStateFlow(ShipmentsViewState())
    val state = _state.asStateFlow()

    init {
        fetchShipments()
    }

    fun fetchShipments() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false, errorMessage = null) }
            try {
                val shipments = api.getUserShipments(relation = "recipient")
                _state.update { it.copy(shipments = shipments, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true, errorMessage = "Nie udało się pobrać przesyłek") }
            }
        }
    }
}