package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.ui.components.LogoCircle
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.components.SecondaryButton

@Composable
fun WelcomeScreen(onRegister: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(48.dp))
        LogoCircle(size = 72.dp)
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Sign up",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Join Kopi dan Nyemil and enjoy more delicious food and drinks!",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton(text = "Register", onClick = onRegister)
        Spacer(Modifier.height(12.dp))
        SecondaryButton(text = "Login", onClick = onLogin)
    }
}
