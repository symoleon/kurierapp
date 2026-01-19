package com.example.kurierapp.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurierapp.NetworkClient
import com.example.kurierapp.profile.ProfileData
import com.example.kurierapp.profile.ProfileViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SendViewModel: ViewModel() {
    private val api = NetworkClient.api
    private val _state = MutableStateFlow(SendViewState())
    val state = _state.asStateFlow()

    fun onFieldChange(newData: SendData) {
        _state.update { it.copy(formData = newData) }
    }

    fun submitForm() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val dataToSend = _state.value.formData
                api.sendShipment(dataToSend)
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
}