package com.kopidannyemil.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.ui.components.AppTextField
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.components.SecondaryButton
import com.kopidannyemil.app.ui.theme.CaramelDark
import com.kopidannyemil.app.ui.theme.CreamCard
import com.kopidannyemil.app.ui.theme.Peach

@Composable
fun ProfileScreen(vm: AppViewModel, padding: PaddingValues, onLogout: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(vm.currentUser?.fullName ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp)
    ) {
        Text(
            text = "Halo ${vm.displayName},",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Profile Saya",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Profile Setting",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CreamCard)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Peach),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = vm.displayName.take(1).uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CaramelDark
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(vm.displayName, fontWeight = FontWeight.Bold)
                    Text(
                        text = "@${vm.currentUser?.username ?: "-"}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        AppTextField(
            value = name,
            onValueChange = { name = it },
            label = "nama",
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.weight(1f))
        PrimaryButton(
            text = "Save",
            onClick = {
                val ok = vm.updateName(name)
                val msg = if (ok) "Profil berhasil disimpan" else "Nama tidak boleh kosong"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        )
        Spacer(Modifier.height(10.dp))
        SecondaryButton(text = "Logout", onClick = onLogout)
    }
}
