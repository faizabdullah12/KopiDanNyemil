package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.data.Category
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.MenuItem
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.theme.CaramelDark
import com.kopidannyemil.app.ui.theme.CreamCard
import com.kopidannyemil.app.ui.theme.Espresso
import com.kopidannyemil.app.ui.theme.Peach

@Composable
fun HomeScreen(vm: AppViewModel, padding: PaddingValues) {
    var filter by remember { mutableStateOf<Category?>(null) }
    val items = DummyData.menu.filter { filter == null || it.category == filter }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Halo ${vm.displayName},",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Mau ngopi atau nyemil apa hari ini?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryChip("Semua", filter == null) { filter = null }
                Category.values().forEach { cat ->
                    CategoryChip(cat.label, filter == cat) { filter = cat }
                }
            }
        }

        items(items, key = { it.id }) { menu ->
            MenuCard(
                menu = menu,
                qty = vm.cart[menu.id] ?: 0,
                onAdd = { vm.addToCart(menu.id) },
                onRemove = { vm.removeFromCart(menu.id) }
            )
        }
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Espresso,
            selectedLabelColor = Color.White
        )
    )
}

@Composable
private fun MenuCard(menu: MenuItem, qty: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CreamCard),
        border = BorderStroke(1.dp, Peach)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = menu.imageRes),
                contentDescription = menu.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Peach)
            )
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(menu.name, fontWeight = FontWeight.Bold)
                Text(
                    text = "• ${menu.description}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = menu.price.toRupiah(),
                        fontWeight = FontWeight.Bold,
                        color = CaramelDark
                    )
                    QtyControl(qty, onAdd, onRemove)
                }
            }
        }
    }
}

@Composable
private fun QtyControl(qty: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    if (qty == 0) {
        RoundAction(label = "+", onClick = onAdd)
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RoundAction(label = "−", onClick = onRemove)
            Text(
                text = qty.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            RoundAction(label = "+", onClick = onAdd)
        }
    }
}

@Composable
private fun RoundAction(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Espresso)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}