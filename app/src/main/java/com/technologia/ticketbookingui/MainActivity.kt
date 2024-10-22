package com.technologia.ticketbookingui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.technologia.ticketbookingui.Presentation.DetailScreen
import com.technologia.ticketbookingui.Presentation.HomeScreen
import com.technologia.ticketbookingui.core.AppRoute
import com.technologia.ticketbookingui.models.MovieModel
import com.technologia.ticketbookingui.ui.theme.TicketBookingUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()

            val modeChange = remember {
                mutableStateOf(false)
            }

            systemUiController.setStatusBarColor(
                color = Color.Red,
                darkIcons = true
            )

            systemUiController.setNavigationBarColor(
                color = Color.Blue,
                darkIcons = true
            )


            TicketBookingUITheme(darkTheme = modeChange.value) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        val navController = rememberNavController()
                        AppRoute.GenerateRoute(
                            navController = navController,
                            isMyMode = modeChange.value,
                            onModeToggle = {
                                modeChange.value =! modeChange.value
                            })
//                        HomeScreen(navHostController = navController, isMyMode = modeChange.value) {
//                            modeChange.value = !modeChange.value
//                        }
//                        DetailScreen(movieModel =   MovieModel(
//                            id = "1",
//                            title = "Minions: The Rise of Gru",
//                            assetImage = R.drawable.minion,
//                            type = "Action",
//                            duration = "1h 27m",
//                            rating = "7.7/10",
//                            synopsis = "A fanboy of a supervillain supergroup known as the Vicious 6, Gru hatches a plan to become evil enough to join them, with the backup of his followers, the Minions.",
//                            isPlaying = true
//                        ))
                    }
                }
            }
        }
    }
}
