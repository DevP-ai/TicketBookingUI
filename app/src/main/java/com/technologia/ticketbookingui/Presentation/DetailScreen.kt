package com.technologia.ticketbookingui.Presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technologia.ticketbookingui.R
import com.technologia.ticketbookingui.models.MovieModel
import com.technologia.ticketbookingui.ui.theme.Yellow

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieModel: MovieModel
) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row {
                FloatButton()
                Spacer(modifier = modifier.width(16.dp))
                FloatButton()
            }
        }
    ) {paddingValues ->

        Column(
            modifier= modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier=modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }

                Text(
                    text = "Movie Details",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Row (modifier= modifier
                .height(320.dp)
                .padding(horizontal = 24.dp)
            ){
                Image(
                    painter = painterResource(id = movieModel.assetImage),
                    contentDescription = movieModel.title,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .weight(0.7f)
                        .height(320.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = modifier
                        .height(320.dp)
                        .weight(0.3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                   MovieInfo(
                       painterRes = R.drawable.baseline_videocam,
                       title = "Genre",
                       value = movieModel.type
                   )

                    MovieInfo(
                        painterRes = R.drawable.baseline_access_time_filled,
                        title = "Duration",
                        value = movieModel.duration
                    )

                    MovieInfo(
                        painterRes = R.drawable.baseline_stars,
                        title = "Rating",
                        value = movieModel.rating
                    )
                }
            }

            Text(
                text = movieModel.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
            )

            Text(
                text = "Synopsis",
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
            )

            Text(
                text = movieModel.synopsis,
                style = MaterialTheme.typography.titleSmall,
                modifier = modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
            )

            Spacer(modifier = Modifier.height(64.dp))

        }

    }
}


@Composable
fun MovieInfo(
    modifier: Modifier = Modifier,
    @DrawableRes painterRes: Int,
    title:String,
    value:String
) {
    Column (
        modifier= modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { }
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(
            painter = painterResource(id = painterRes)
            , contentDescription = title)
        Spacer(modifier = modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun FloatButton(modifier: Modifier=Modifier) {
    Button(modifier = modifier
        .wrapContentWidth()
        .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
        onClick = {}) {
        Text(text = "Booking Now")
    }
}