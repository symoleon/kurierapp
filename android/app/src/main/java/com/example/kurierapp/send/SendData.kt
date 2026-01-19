package com.example.kurierapp.send

import com.example.kurierapp.profile.ProfileData
import com.example.kurierapp.shipments.Address
import com.example.kurierapp.shipments.ShipmentData
import com.example.kurierapp.shipments.Size

data class SendData(
    val recipientPhone: String = "",
    val recipientAddress: Address = Address("", "", "", "", ""),
    val size: Size = Size.S,
    val name: String = "",
)

data class SendViewState(
    val formData: SendData = SendData(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)