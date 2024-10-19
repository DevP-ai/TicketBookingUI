package com.technologia.ticketbookingui.Presentation

import android.widget.Space
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.technologia.ticketbookingui.R
import com.technologia.ticketbookingui.ui.theme.Gray
import com.technologia.ticketbookingui.ui.theme.Red
import com.technologia.ticketbookingui.ui.theme.Yellow
import kotlinx.coroutines.delay

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

            Text(
                text = "Book Your Favourite Movie Here!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = modifier.height(24.dp))

            Banner()

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

@Preview
@Composable
fun Banner(modifier: Modifier = Modifier) {

    val banners = listOf(
        R.drawable.banner_1,
        R.drawable.banner_2,
        R.drawable.banner_3
    )


    val pagerState = rememberPagerState(pageCount = {banners.size})
    val bannerIndex = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.collect{page->
            bannerIndex.value = page
        }
    }

    LaunchedEffect(Unit) {
        while (true){
            delay(2_000)
            tween<Float>(1000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage+1) % pagerState.pageCount
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = 15.dp)
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {page->
            Image(
                painter = painterResource(id = banners[page]),
                contentDescription = "Banners",
                contentScale = ContentScale.FillBounds,
                modifier = modifier.clip(shape = RoundedCornerShape(8.dp))
                )
        }

        Row (
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ){
            repeat(banners.size){index->
                val height = if(index == bannerIndex.value) 11.dp else 10.dp
                val width = if(index == bannerIndex.value) 11.dp else 10.dp
                val color = if(index == bannerIndex.value) Red  else Gray

                Surface(
                    modifier = modifier
                        .padding(end = 6.dp)
                        .size(width, height)
                        .clip(RoundedCornerShape(20.dp)),
                    color = color
                ){}
            }
        }
    }

}