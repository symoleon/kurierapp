package com.example.kurierapp.shipments

data class ShipmentData(
    val id: String,
    val sender: String,
    val address: Address,
    val size: Size,
)

data class Address(
    val city: String,
    val postalCode: String,
    val street: String,
    val buildingNumber: String,
    val apartmentNumber: String,
)

enum class Size{
    S, M, L, XL
}
