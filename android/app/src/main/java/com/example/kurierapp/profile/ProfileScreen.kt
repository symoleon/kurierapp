package com.example.kurierapp.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.log

@Composable
@Preview(showBackground = true)
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    logout: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val formData = state.formData

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Dane profilu", style = MaterialTheme.typography.headlineMedium)
        CustomTextField(
            value = formData.name,
            label = "Nazwa",
            onValueChange = {viewModel.onFieldChange(formData.copy(name = it))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.email,
            label = "Email",
            onValueChange = {viewModel.onFieldChange(formData.copy(email = it))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.phone,
            label = "Telefon",
            onValueChange = {viewModel.onFieldChange(formData.copy(phone = it))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.address.city,
            label = "Miasto",
            onValueChange = { viewModel.onFieldChange(formData.copy(address = formData.address.copy(city = it)))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.address.postal_code,
            label = "Kod pocztowy",
            onValueChange = { viewModel.onFieldChange(formData.copy(address = formData.address.copy(postal_code = it)))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.address.street?: "",
            label = "Ulica",
            onValueChange = { viewModel.onFieldChange(formData.copy(address = formData.address.copy(street = it)))},
            imeAction = ImeAction.Next,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.spacedBy(12.dp),
        ) {
            CustomTextField(
                value = formData.address.building_no,
                label = "Nr bud.",
                onValueChange = { viewModel.onFieldChange(formData.copy(address = formData.address.copy(building_no = it)))},
                modifier = Modifier.weight(0.6f),
                imeAction = ImeAction.Next,
            )
            CustomTextField(
                value = formData.address.apartment_no?: "",
                label = "Nr miesz.",
                onValueChange = { viewModel.onFieldChange(formData.copy(address = formData.address.copy(apartment_no = it)))},
                modifier = Modifier.weight(0.4f),
                imeAction = ImeAction.Next,
            )
        }
        if (state.isError && state.errorMessage != null) {
            Text(
                text = "Błąd: ${state.errorMessage}",
                color = MaterialTheme.colorScheme.error,
            )
        }

        if (state.isSuccess) {
            Text(
                text = "Dane zostały pomyślnie zaaktualizowane",
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Button(
            onClick = { viewModel.submitForm() },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = !state.isLoading,
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                Text("Zapisz")
            }
        }
        Button(
            onClick = { logout() },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = !state.isLoading,
        ) {
            Text("Wyloguj")
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}