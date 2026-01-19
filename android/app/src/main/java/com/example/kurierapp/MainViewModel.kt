package com.example.kurierapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val api = NetworkClient.api
    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        if (TokenManager.getToken() != null) {
            _state.update { it.copy(isLoggedIn = true) }
        }
    }
    fun onLoginChange(l: String) {
        _state.update { it.copy(login = l) }
    }
    fun onPasswordChange(p: String) {
        _state.update { it.copy(password = p) }
    }
    fun performLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val creds = LoginRequest(_state.value.login, _state.value.password)
                val response = api.login(creds)
                val token = response.string()
                if (token.isNotBlank()) {
                    TokenManager.saveToken(token)
                    _state.update { it.copy(isLoggedIn = true, isLoading = false) }
                } else {
                    _state.update { it.copy(isLoading = false, isError = true) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
    fun logout() {
        TokenManager.clearToken()
        _state.update { it.copy(isLoggedIn = false) }
    }
}