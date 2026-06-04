package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Updated AuthScreen with Scroll support and focused state management.
 */
@Composable
fun AuthScreen(
    isSignUp: Boolean,
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onBackToWelcome: () -> Unit,
    onSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Added scroll state so the UI doesn't break when the keyboard appears
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState), // Prevents clipping on small screens
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. HEADER SECTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToWelcome) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back to Welcome",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = if (isSignUp) "SIGN UP" else "SIGN IN",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Text(
            text = if (isSignUp)
                "Enter your name, email id and password, to register with us."
            else
                "Please sign in to continue your collaborative planning.",
            modifier = Modifier.padding(vertical = 40.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 22.sp
        )

        // --- 2. FORM CARD SECTION ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isSignUp) {
                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name"
                    )
                }

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email"
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                BlueGradientButton(
                    text = if (isSignUp) "SIGN UP" else "SIGN IN",
                    onClick = {
                        onSuccess()
                        // Basic validation check before success
//                        if (email.isNotEmpty() && password.isNotEmpty()) {
//                            onSuccess()
//                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- 3. BOTTOM NAVIGATION TOGGLE ---
        Text(
            text = if (isSignUp)
                "Already have an account? Sign In"
            else
                "Don't have an account? Sign Up",
            color = Color(0xFF2196F3),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable {
                    if (isSignUp) onNavigateToSignIn() else onNavigateToSignUp()
                }
        )
    }
}