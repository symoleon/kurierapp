package com.example.kurierapp.shipments

data class ShipmentData(
    val id: String,
    val sender: String,
    val address: Address,
    val size: Size,
    val name: String,
    val state: ShipmentState,
)

data class Address(
    val city: String,
    val postal_code: String,
    val street: String?,
    val building_no: String,
    val apartment_no: String?,
)

enum class Size{
    S, M, L, XL
}
enum class ShipmentState{
    created, paid, sent, delivered
}

data class ShipmentsViewState(
    val shipments: List<ShipmentData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)
