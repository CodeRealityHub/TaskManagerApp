package com.example.teamtaskerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamtaskerapp.ui.models.Board
import kotlinx.coroutines.launch
import com.example.teamtaskerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHomePage(
    onProfileClick: () -> Unit,
    onCreateBoardClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onBoardClick: (Board) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val boards = listOf(
        Board("Spongy Board", "SpongeBob Squarespace", R.drawable.baseline_other_houses_24),
        Board("Noddy's Board", "noddy", R.drawable.user),
        Board("Ryan's Board", "Royanurag@997", R.drawable.outline_add_home_24)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color.White,
                drawerShape = RoundedCornerShape(0.dp)
            ) {
                // Pass the callbacks to the Drawer
                DrawerContent(
                    onProfileClick = {
                        scope.launch { drawerState.close() }
                        onProfileClick()
                    },
                    onSignOutClick = {
                        scope.launch { drawerState.close() }
                        onSignOutClick()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TeamTasker", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE57373)),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onCreateBoardClick, // Navigates to Create Board
                    containerColor = Color(0xFFE57373),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) {
                items(boards) { board ->
                    BoardItem(
                        board = board,
                        onClick = { onBoardClick(board)}
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    onProfileClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE57373))
                .padding(24.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.baseline_other_houses_24),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Spongebobs Squarepantu",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        NavigationDrawerItem(
            label = { Text("My Profile", fontWeight = FontWeight.Bold) },
            selected = false,
            onClick = onProfileClick, // Fixed: Uses passed lambda
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
        )

        NavigationDrawerItem(
            label = { Text("Sign Out", fontWeight = FontWeight.Bold) },
            selected = false,
            onClick = onSignOutClick, // Fixed: Triggers Sign Out
            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
        )
    }
}

@Composable
fun BoardItem(
    board: Board,
    onClick: () -> Unit
    ) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = board.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = board.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "CreatedBy: ${board.author}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}