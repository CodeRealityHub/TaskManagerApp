package com.example.teamtaskerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.teamtaskerapp.ui.theme.TeamTaskerAppTheme
import com.example.teamtaskerapp.ui.views.*

/**
 * Enum class to define all available screens in the app.
 * This helps manage navigation state clearly.
 */
enum class AppScreen {
    Welcome,
    SignIn,
    SignUp,
    Home,
    MyProfile,
    CreateBoard,
    TaskDetails,
    Members,
    CardDetails
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Allows the app to draw behind status and navigation bars

        setContent {
            TeamTaskerAppTheme {
                // This state variable determines which screen is currently visible.
                // We start with the Welcome screen.
                var currentScreen by remember { mutableStateOf(AppScreen.Welcome) }
                var selectedTaskName by remember { mutableStateOf("") }
                var selectedBoardName by remember { mutableStateOf("") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // The Box acts as a container for our screens,
                    // respecting the system bar padding (innerPadding).
                    Box(modifier = Modifier.padding(innerPadding)) {

                        // Crossfade provides a smooth "fade" animation when
                        // currentScreen changes.
                        Crossfade(targetState = currentScreen, label = "MainNavigation") { screen ->
                            when (screen) {
                                AppScreen.Welcome -> TeamTaskerWelcomeScreen(
                                    onGetStarted = { currentScreen = AppScreen.SignIn }
                                )

                                AppScreen.SignIn -> AuthScreen(
                                    isSignUp = false,
                                    onNavigateToSignIn = { currentScreen = AppScreen.SignIn },
                                    onNavigateToSignUp = { currentScreen = AppScreen.SignUp },
                                    onBackToWelcome = { currentScreen = AppScreen.Welcome },
                                    onSuccess = { currentScreen = AppScreen.Home }
                                )

                                AppScreen.SignUp -> AuthScreen(
                                    isSignUp = true,
                                    onNavigateToSignIn = { currentScreen = AppScreen.SignIn },
                                    onNavigateToSignUp = { currentScreen = AppScreen.SignUp },
                                    onBackToWelcome = { currentScreen = AppScreen.Welcome },
                                    onSuccess = { currentScreen = AppScreen.Home }
                                )

                                AppScreen.Home -> AppHomePage(
                                    onProfileClick = { currentScreen = AppScreen.MyProfile },
                                    onCreateBoardClick = { currentScreen = AppScreen.CreateBoard },
                                    onSignOutClick = { currentScreen = AppScreen.Welcome },
                                    onBoardClick = { board ->
                                        // Here you would navigate to your Task Screen
                                        // For now, let's just log it or change state
                                        println("Clicked on ${board.title}")
                                         currentScreen = AppScreen.TaskDetails
                                    }
                                )

                                AppScreen.CardDetails -> CardDetailsPage(
                                    taskName = selectedTaskName, // Pass real task data here
                                    onBackClick = { currentScreen = AppScreen.TaskDetails },
                                    onUpdateClick = {
                                        // Add logic to save changes here
                                        currentScreen = AppScreen.TaskDetails
                                    },
                                    onDeleteConfirm = {
                                        // 1. Logic to remove card from your data list would go here
                                        // 2. Navigate back to the board
                                        currentScreen = AppScreen.TaskDetails
                                    }
                                )

                                AppScreen.TaskDetails -> TaskBoardPage(
                                    boardTitle = selectedBoardName, // You can pass the actual board title here
                                    onBackClick = { currentScreen = AppScreen.Home },
                                    onMembersClick = { currentScreen = AppScreen.Members },
                                    onCardClick = { task ->
                                        // 1. Store the task info if needed
                                        // 2. Navigate to the details page
                                        selectedTaskName = task.title
                                        currentScreen = AppScreen.CardDetails
                                    }
                                )

                                AppScreen.MyProfile -> UserProfilePage (
                                    onBackClick = { currentScreen = AppScreen.Home }
                                )

                                AppScreen.CreateBoard -> CreateBoardScreen(
                                    onBackClick = { currentScreen = AppScreen.Home }
                                )

                                AppScreen.Members -> {
                                    MembersPage(
                                        onBackClick = { currentScreen = AppScreen.TaskDetails }
                                    )
                                }

                                else ->  TeamTaskerWelcomeScreen(
                                onGetStarted = { currentScreen = AppScreen.SignIn }
                            )
                            }
                        }
                    }
                }
            }
        }
    }
}