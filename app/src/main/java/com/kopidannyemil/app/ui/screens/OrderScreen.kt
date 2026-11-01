package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.components.AppTextField
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.theme.CaramelDark
import com.kopidannyemil.app.ui.theme.CreamCard
import com.kopidannyemil.app.ui.theme.Peach

@Composable
fun OrderScreen(vm: AppViewModel, padding: PaddingValues, onOrdered: () -> Unit) {
    var name by remember { mutableStateOf(vm.currentUser?.fullName ?: "") }
    var address by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }
    val lines = vm.cartLines

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Alamat",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Isi data pengiriman pesananmu",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(20.dp))

        AppTextField(value = name, onValueChange = { name = it }, label = "Nama Lengkap")
        Spacer(Modifier.height(12.dp))
        AppTextField(
            value = address,
            onValueChange = { address = it },
            label = "Alamat",
            singleLine = false,
            minLines = 3
        )
        Spacer(Modifier.height(12.dp))
        AppTextField(
            value = detail,
            onValueChange = { detail = it },
            label = "Patokan atau detail lengkap",
            singleLine = false,
            minLines = 3,
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Ringkasan pesanan",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CreamCard),
            border = BorderStroke(1.dp, Peach)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (lines.isEmpty()) {
                    Text(
                        text = "Keranjang masih kosong. Pilih menu di tab Home dulu ya.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    lines.forEach { line ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${line.qty}x ${line.item.name}",
                                modifier = Modifier.weight(1f)
                            )
                            Text(line.subtotal.toRupiah())
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Peach)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", fontWeight = FontWeight.Bold)
                        Text(
                            text = vm.cartTotal.toRupiah(),
                            fontWeight = FontWeight.Bold,
                            color = CaramelDark
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        PrimaryButton(
            text = "Order dan Kirim",
            enabled = lines.isNotEmpty() && name.isNotBlank() && address.isNotBlank(),
            onClick = {
                vm.placeOrder(name.trim(), address.trim(), detail.trim())
                onOrdered()
            }
        )
    }
}
