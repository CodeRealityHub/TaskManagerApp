package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.teamtaskerapp.ui.models.BoardList
import com.example.teamtaskerapp.ui.models.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskBoardPage(
    boardTitle: String,
    onBackClick: () -> Unit,
    onMembersClick: () -> Unit,
    onCardClick: (TaskCard) -> Unit
) {
    // --- DO NOT EDIT START ---
    val boardLists = listOf(
        BoardList(
            "First Draft", listOf(
                TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
                TaskCard("sponge me please", listOf(R.drawable.user)),
                TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
                TaskCard("sponge me please", listOf(R.drawable.user)),
                TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
                TaskCard("sponge me please", listOf(R.drawable.user))
            )
        ),
        BoardList("UI List", listOf(
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
        )
        ),
        BoardList("Developer List", listOf(
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
        )
        ),
        BoardList("Deployment List", listOf(
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
        )
        ),
        BoardList("Testing List", listOf(
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
            TaskCard("sponge me", listOf(R.drawable.user, R.drawable.user)),
            TaskCard("sponge me please", listOf(R.drawable.user)),
        )
        )
    )
    // --- DO NOT EDIT END ---

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(boardTitle, color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE57373)),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(
                                text = { Text("Members") },
                                onClick = {
                                    showMenu = false
                                    onMembersClick()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(boardLists) { list ->
                BoardColumn(
                    boardList = list,
                    onCardClick = onCardClick
                )
            }
        }
    }
}

@Composable
fun BoardColumn(
    boardList: BoardList,
    onCardClick: (TaskCard) -> Unit
) {
    val taskScrollState = rememberScrollState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // NEW STATES for Adding Card UI
    var isAddingCard by remember { mutableStateOf(false) }
    var newCardName by remember { mutableStateOf("") }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete List", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete this list and all its cards? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("DELETE", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("CANCEL", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(8.dp)
        )
    }

    Card(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFFEEEEEE), shadowElevation = 2.dp) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = boardList.title, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))

                    IconButton(onClick = {
                        // We pass the first task of the list as a reference to open the details
                        // Or you can pass a specific 'TaskCard' object here
                        if (boardList.tasks.isNotEmpty()) {
                            onCardClick(boardList.tasks[0])
                        }
                     }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = { showDeleteDialog = true }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .verticalScroll(taskScrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                boardList.tasks.forEach { task ->
                    val stripeColor = Color(0xFF4CAF50)
                    TaskItemCard(
                        task = task,
                        topColor = stripeColor,
                        onClick = { onCardClick(task) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- UPDATED FOOTER LOGIC ---
            Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                if (isAddingCard) {
                    // Custom TextField View matching Image 2
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel button
                        IconButton(
                            onClick = {
                                isAddingCard = false
                                newCardName = ""
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Cancel, contentDescription = "Cancel", tint = Color.Black)
                        }

                        // Input field
                        BasicTextField(
                            value = newCardName,
                            onValueChange = { newCardName = it },
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            decorationBox = { innerTextField ->
                                if (newCardName.isEmpty()) {
                                    Text("List Name", color = Color.Gray, fontSize = 16.sp)
                                }
                                innerTextField()
                            }
                        )

                        // Confirm button
                        IconButton(
                            onClick = {
                                if (newCardName.isNotEmpty()) {
                                    /* Add logic here */
                                    isAddingCard = false
                                    newCardName = ""
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Confirm", tint = Color.Black)
                        }
                    }
                } else {
                    // Standard Button
                    Button(
                        onClick = { isAddingCard = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3F2FD)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Add Card", color = Color(0xFFE57373), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItemCard(
    task: TaskCard,
    topColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(topColor))
            Column(modifier = Modifier.padding(12.dp)) {
                Text(task.title, fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    task.members.forEach { img ->
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}