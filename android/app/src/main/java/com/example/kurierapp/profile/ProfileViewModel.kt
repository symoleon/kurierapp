package com.example.kurierapp.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurierapp.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val api = NetworkClient.api
    private val _state = MutableStateFlow(ProfileViewState())
    val state = _state.asStateFlow()

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            try {
                val remoteData = api.getCurrentUser()
                _state.update {
                    it.copy(
                        isLoading = false,
                        formData = remoteData
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true, errorMessage = "Nie udało się pobrać danych") }
            }
        }
    }
    fun onFieldChange(newData: ProfileData) {
        _state.update { it.copy(formData = newData) }
    }

    fun submitForm() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val dataToSend = _state.value.formData
                api.updateCurrentUser(dataToSend)
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
}