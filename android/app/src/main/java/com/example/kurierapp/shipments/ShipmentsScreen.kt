package com.example.kurierapp.shipments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview(showBackground = true)
fun ShipmentsScreen(
    viewModel: ShipmentViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsState().value
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { viewModel.fetchShipments() },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(state.shipments) { shipmentData ->
                Shipment(
                    name = shipmentData.name ?: "Brak nazwy",
                    shipmentState = shipmentData.state,
                    size = shipmentData.size,
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
    }
}

@Composable
fun Shipment(
    name: String,
    shipmentState: ShipmentState,
    size: Size,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp)
    ) {
        Text(text = "Przesyłka: ${name}", modifier = Modifier.padding(4.dp))
        var status = ""
        when(shipmentState) {
            ShipmentState.created -> status = "utworzona"
            ShipmentState.paid -> status = "opłacona"
            ShipmentState.sent -> status = "wysłana"
            ShipmentState.delivered -> status = "odebrana"
        }
        Text("Status: ${status}", modifier = Modifier.padding(4.dp))
        Text("Rozmiar: ${size.name}", modifier = Modifier.padding(4.dp))
    }
}