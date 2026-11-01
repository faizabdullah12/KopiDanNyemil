package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.theme.CaramelDark
import com.kopidannyemil.app.ui.theme.CreamCard
import com.kopidannyemil.app.ui.theme.Mint
import com.kopidannyemil.app.ui.theme.MintDark
import com.kopidannyemil.app.ui.theme.Peach

@Composable
fun ConfirmationScreen(vm: AppViewModel, onOk: () -> Unit) {
    val order = vm.lastOrder

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Halo ${vm.displayName},",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Mint),
                contentAlignment = Alignment.Center
            ) {
                Text("OK", color = MintDark, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Terima kasih sudah memesan, tunggu aja sebentar kurir kami segera mengantarkan pesananmu!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (order != null) {
                Spacer(Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamCard),
                    border = BorderStroke(1.dp, Peach)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dikirim ke", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(order.receiverName, fontWeight = FontWeight.Bold)
                        Text(order.address)
                        if (order.detail.isNotBlank()) {
                            Text(
                                text = order.detail,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Peach)
                        order.lines.forEach { line ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${line.qty}x ${line.item.name}", modifier = Modifier.weight(1f))
                                Text(line.subtotal.toRupiah())
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Peach)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total", fontWeight = FontWeight.Bold)
                            Text(order.total.toRupiah(), fontWeight = FontWeight.Bold, color = CaramelDark)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        PrimaryButton(text = "Oke", onClick = onOk)
    }
}
