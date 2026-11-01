package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.ui.components.AppTextField
import com.kopidannyemil.app.ui.components.ErrorText
import com.kopidannyemil.app.ui.components.PrimaryButton

@Composable
fun RegisterScreen(
    vm: AppViewModel,
    onBack: () -> Unit,
    onGoLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Buat akun baru, gratis.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(28.dp))

        AppTextField(
            value = fullName,
            onValueChange = { fullName = it; errorMsg = null },
            label = "nama lengkap"
        )
        Spacer(Modifier.height(12.dp))
        AppTextField(
            value = username,
            onValueChange = { username = it; errorMsg = null },
            label = "username"
        )
        Spacer(Modifier.height(12.dp))
        AppTextField(
            value = password,
            onValueChange = { password = it; errorMsg = null },
            label = "password",
            isPassword = true,
            imeAction = ImeAction.Done
        )
        Spacer(Modifier.height(8.dp))
        ErrorText(errorMsg)

        Spacer(Modifier.weight(1f))
        TextButton(
            onClick = onGoLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sudah punya akun? Login")
        }
        PrimaryButton(
            text = "Register",
            onClick = {
                errorMsg = vm.register(fullName, username, password)
                if (errorMsg == null) onSuccess()
            }
        )
    }
}
