package com.example.kurierapp.profile

data class AddressData(
    val city: String = "",
    val postal_code: String = "",
    val street: String? = "",
    val building_no: String = "",
    val apartment_no: String? = "",
)

data class ProfileData(
    val name: String = "",
    val address: AddressData = AddressData(),
    val phone: String = "",
    val email: String = "",
)

data class ProfileViewState(
    val formData: ProfileData = ProfileData(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)
