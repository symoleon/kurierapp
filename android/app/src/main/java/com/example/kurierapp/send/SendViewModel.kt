package com.example.kurierapp.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurierapp.NetworkClient
import com.example.kurierapp.PaymentData
import com.example.kurierapp.profile.ProfileData
import com.example.kurierapp.profile.ProfileViewState
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SendViewModel: ViewModel() {
    private val api = NetworkClient.api
    private val _state = MutableStateFlow(SendViewState())
    val state = _state.asStateFlow()

    private val _paymentLaunchData = MutableStateFlow<PaymentData?>(null)
    val paymentLaunchData = _paymentLaunchData.asStateFlow()

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

    fun checkout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val response = api.getPaymentDetails()
                _paymentLaunchData.value = response
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
    fun onPaymentResult(paymentResult: PaymentSheetResult) {
        _paymentLaunchData.value = null

        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                submitForm()
            }
            is PaymentSheetResult.Canceled -> {
                _state.update { it.copy(isLoading = false) }
            }
            is PaymentSheetResult.Failed -> {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
}