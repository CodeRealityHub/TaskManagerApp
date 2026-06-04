package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamtaskerapp.R

// Temporary UI State for MyProfile
data class ProfileUiState(
    var name: String = "Spongebobs Squarepantu",
    var email: String = "squeeze.sponge23@gmail.com",
    var mobile: String = "1234567890",
    val profileImageRes: Int = R.drawable.team // Use your asset name
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfilePage(onBackClick: () -> Unit) {
    // In a real app, this state would come from a ViewModel linked to a Database
    var uiState by remember { mutableStateOf(ProfileUiState()) }
    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Profile",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE57373) // Coral theme color from image
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Main Content Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Profile Image
                    Image(
                        painter = painterResource(id = uiState.profileImageRes),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // 2. Name Input (Coral colored top line in image)
                    TextField(
                        value = uiState.name,
                        onValueChange = { uiState = uiState.copy(name = it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color(0xFFE57373), // Specific color
                            focusedIndicatorColor = Color(0xFFE57373)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3. Reusable Custom Input Fields (to reduce code duplicate)
                    ProfileInputField(
                        label = "Email",
                        value = uiState.email,
                        onValueChange = { uiState = uiState.copy(email = it) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileInputField(
                        label = "Mobile",
                        value = uiState.mobile,
                        onValueChange = { uiState = uiState.copy(mobile = it) }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // 4. Update Button (Gradient Style)
                    BlueGradientButton(
                        text = "UPDATE",
                        onClick = {
                            focusManager.clearFocus()
                            /* Handle Save logic here */
                        }
                    )
                }
            }
        }
    }
}

// Reusable component for basic input fields in this screen
@Composable
fun ProfileInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = Color(0xFFE57373)
            )
        )
    }
}

