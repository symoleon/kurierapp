package com.example.kurierapp.send

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kurierapp.NetworkClient
import com.example.kurierapp.shipments.Size
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet

@Composable
@Preview(showBackground = true)
fun SendScreen(
    viewModel: SendViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val formData = state.formData
    val context = LocalContext.current
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    val paymentLaunchData by viewModel.paymentLaunchData.collectAsState()
    val paymentSheet = rememberPaymentSheet(viewModel::onPaymentResult)

    LaunchedEffect(paymentLaunchData) {
        paymentLaunchData?.let { data ->
            PaymentConfiguration.init(context, "pk_test_51Skr2qAK0IlO7ATnsMts9u6t0uMlsAbtlAvmErtsP4sTOS9dvtEPjDqS00tRyB14YYs7Kw09J6olHIxtb5m2H6q600vp4WbOpM")
            paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret = data.paymentIntent,
                configuration = PaymentSheet.Configuration(
                    merchantDisplayName = "Kurier App",
                )
            )
        }
    }


    val scrollState = rememberScrollState()
    val sizeOptions = listOf("S", "M", "L", "XL")

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Dane przesyłki", style = MaterialTheme.typography.headlineMedium)
        CustomTextField(
            value = formData.name,
            label = "Nazwa paczki",
            onValueChange = { viewModel.onFieldChange(formData.copy(name = it)) },
            imeAction = ImeAction.Next,
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            Size.entries.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = formData.size == option,
                    onClick = { viewModel.onFieldChange(formData.copy(size = option)) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = sizeOptions.size),
                    label = { Text(option.name) }
                )
            }
        }
        CustomTextField(
            value = formData.recipientPhone,
            label = "Telefon odbiorcy",
            onValueChange = {viewModel.onFieldChange(formData.copy(recipientPhone = it))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.recipientAddress.city,
            label = "Miasto",
            onValueChange = { viewModel.onFieldChange(formData.copy(recipientAddress = formData.recipientAddress.copy(city = it)))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.recipientAddress.postal_code,
            label = "Kod pocztowy",
            onValueChange = { viewModel.onFieldChange(formData.copy(recipientAddress = formData.recipientAddress.copy(postal_code = it)))},
            imeAction = ImeAction.Next,
        )
        CustomTextField(
            value = formData.recipientAddress.street?: "",
            label = "Ulica",
            onValueChange = { viewModel.onFieldChange(formData.copy(recipientAddress = formData.recipientAddress.copy(street = it)))},
            imeAction = ImeAction.Next,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.spacedBy(12.dp),
        ) {
            CustomTextField(
                value = formData.recipientAddress.building_no,
                label = "Nr bud.",
                onValueChange = { viewModel.onFieldChange(formData.copy(recipientAddress = formData.recipientAddress.copy(building_no = it)))},
                modifier = Modifier.weight(0.6f),
                imeAction = ImeAction.Next,
            )
            CustomTextField(
                value = formData.recipientAddress.apartment_no?: "",
                label = "Nr miesz.",
                onValueChange = { viewModel.onFieldChange(formData.copy(recipientAddress = formData.recipientAddress.copy(apartment_no = it)))},
                modifier = Modifier.weight(0.4f),
                imeAction = ImeAction.Next,
            )
        }
        Button(
            onClick = {
                viewModel.checkout()
                      },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        ) {
            Text("Nadaj paczkę")
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
