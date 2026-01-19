package com.example.kurierapp

data class MainUiState(
    val isLoggedIn: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
