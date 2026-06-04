package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamtaskerapp.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsPage(
    taskName: String,
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteConfirm: () -> Unit
) {
    // --- 1. STATES ---
    var currentCardName by remember { mutableStateOf(taskName) }
    var selectedColor by remember { mutableStateOf(Color(0xFF4CAF50)) }
    var showDeleteWarning by remember { mutableStateOf(false) }

    var showColorPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showMemberDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    // Fixed Locale handling
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val dateDisplay = datePickerState.selectedDateMillis?.let {
        formatter.format(Date(it))
    } ?: "14/05/2024"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentCardName, color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE57373)),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteWarning = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            )
        }
    ) { padding -> // Content lambda starts here

        // --- 1. DIALOGS (Inside Scaffold to respect padding/scrim) ---
        if (showDeleteWarning) {
            AlertDialog(
                onDismissRequest = { showDeleteWarning = false },
                title = { Text(text = "Delete Card", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to delete '$currentCardName'? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        showDeleteWarning = false
                        onDeleteConfirm()
                    }) {
                        Text("DELETE", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteWarning = false }) {
                        Text("CANCEL", color = Color.Gray)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
        }

        // --- 2. MAIN CONTENT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SECTION 1: CARD NAME
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Card Name", color = Color(0xFFE57373), fontSize = 12.sp)
                    BasicTextField(
                        value = currentCardName,
                        onValueChange = { currentCardName = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                        decorationBox = { innerTextField ->
                            Column {
                                innerTextField()
                                HorizontalDivider(
                                    color = Color(0xFFE57373),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    )
                }
            }

            // SECTION 2: SETTINGS (Color, Members, Date)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Label Color", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(selectedColor)
                            .clickable { showColorPicker = true }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Members", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(2) {
                            Image(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp).padding(end = 8.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { showMemberDialog = true }
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, null, tint = Color(0xFF2196F3), modifier = Modifier.size(32.dp))
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = Color.Transparent,
                                shape = CircleShape,
                                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF2196F3))
                            ) {}
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Due Date", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dateDisplay,
                        modifier = Modifier.clickable { showDatePicker = true },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = onUpdateClick,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                        Text("UPDATE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    } // Scaffold body ends here

    // --- 3. EXTERNAL DIALOGS (Not strictly inside Scaffold) ---
    if (showMemberDialog) {
        SearchMemberDialog(
            onDismiss = { showMemberDialog = false },
            onAdd = { showMemberDialog = false }
        )
    }

    if (showColorPicker) {
        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            title = { Text("Select Label Color") },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val colors = listOf(Color.Red, Color(0xFF4CAF50), Color.Blue, Color.Yellow, Color.Magenta)
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(color, CircleShape)
                                .clickable {
                                    selectedColor = color
                                    showColorPicker = false
                                }
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("CANCEL") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}