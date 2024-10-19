package com.technologia.ticketbookingui.Presentation

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    isMyMode:Boolean,
    onModeToggle:()->Unit
    )
{
    val scrollState = rememberScrollState()

    var isDarkMode by remember {
        mutableStateOf(isMyMode)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Welcome back,sir",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 24.dp)
                )

                DarkModeToggle(
                    isDarkTheme = isDarkMode,
                    onToggleChange = {
                        onModeToggle()
                        isDarkMode =!isDarkMode
                    }
                )
            }
            Spacer(modifier = modifier.height(4.dp))
        }

    }
}

@Composable
fun DarkModeToggle(
    isDarkTheme: Boolean,
    onToggleChange:(Boolean)->Unit
) {
    Row (
        modifier = Modifier.padding(end = 20.dp)
    ){
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onToggleChange)
    }
}