package com.example.kurierapp.shipments

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShipmentsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Shipment(
                modifier
                    .fillParentMaxWidth()
            )
        }

    }
}

@Composable
fun Shipment(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Text(text = "Przesyłka", modifier = Modifier.padding(4.dp))
    }
}