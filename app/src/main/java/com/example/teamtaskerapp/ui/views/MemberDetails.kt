package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.teamtaskerapp.ui.models.Member
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.teamtaskerapp.R


// Main Members Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembersScreen(onBackClick: () -> Unit) {
    // State to control the visibility of the "Add Member" dialog
    var showAddMemberDialog by remember { mutableStateOf(false) }

    // Mock data for the members list
    val membersList = listOf(
        Member("noddy", "noddy.cartoon34@gmail.com", R.drawable.outline_android_24),
        Member("Spongebobs Squarepantu", "squeeze.sponge23@gmail.com", R.drawable.outline_android_24),
        Member("Royanurag@997", "anurag.roy12@gmail.com", R.drawable.outline_android_24)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Members",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Go Back",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                actions = {
                    // This icon represents the "Add Member" action
                    IconButton(onClick = { showAddMemberDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Member",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE57373) // Warm reddish-pink color from image
                )
            )
        },
        containerColor = Color(0xFFF9F9F9) // Very light gray background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp) // External padding around the main card
        ) {
            // Main card containing the list of members
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Allow card to fill remaining space
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                // List of members using LazyColumn for performance
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp) // Bottom padding for last item
                ) {
                    items(membersList) { member ->
                        MemberListItem(member = member)
                        // Divider between list items
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFE0E0E0), // Light gray divider
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }

    // Displays the "Search Member" dialog when the state is true
    if (showAddMemberDialog) {
        AddMemberDialog(onDismiss = { showAddMemberDialog = false })
    }
}

// Composible function to render an individual member list item
@Composable
fun MemberListItem(member: Member) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular profile picture
        Image(
            painter = painterResource(id = member.profilePictureResId),
            contentDescription = "Profile Picture of ${member.name}",
            modifier = Modifier
                .size(60.dp) // Size from image
                .clip(CircleShape)
                .background(Color.LightGray), // Placeholder background
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Column containing the member name and email
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = member.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = member.email,
                fontSize = 14.sp,
                color = Color(0xFF757575) // Gray color for email
            )
        }
    }
}

// Composible function to render the "Search Member" dialog
@Composable
fun AddMemberDialog(onDismiss: () -> Unit) {
    // State to hold the text input for the email field
    var emailInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        // Card container for the dialog with elevation and rounded corners
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(4.dp), // Sharper corners for the dialog
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp) // Specific padding from image
            ) {
                // Monospaced dialog title
                Text(
                    text = "Search Member",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )

                // Subtitle/Instructions
                Text(
                    text = "Search by email to add member to the board.",
                    fontSize = 14.sp,
                    color = Color(0xFF757575), // Gray subtitle
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                )

                // Custom basic text input field mimicking the image's style
                BasicInputWithLabel(
                    label = "Email",
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    labelColor = Color(0xFFE57373) // Warm pink label color
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Row containing the ADD and CANCEL action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // CANCEL button with lighter text color
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "CANCEL",
                            color = Color(0xFFEF9A9A), // Light reddish color
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // ADD button with primary blue color
                    TextButton(onClick = {
                        // Implement adding member logic here
                        onDismiss()
                    }) {
                        Text(
                            text = "ADD",
                            color = Color(0xFF2196F3), // Primary blue color
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// Helper composable for the label-styled text input field
@Composable
fun BasicInputWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    labelColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = labelColor,
            fontWeight = FontWeight.Medium
        )
        // Standard Jetpack Compose BasicTextField with customized appearance
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            decorationBox = { innerTextField ->
                Column {
                    innerTextField()
                    // Bottom border mimic
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                }
            }
        )
    }
}
