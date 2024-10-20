package com.technologia.ticketbookingui.Presentation

import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavHostController
import com.technologia.ticketbookingui.R
import com.technologia.ticketbookingui.models.nowPlaying
import com.technologia.ticketbookingui.models.upcomingMovie
import com.technologia.ticketbookingui.ui.theme.BlueVariant
import com.technologia.ticketbookingui.ui.theme.Gray
import com.technologia.ticketbookingui.ui.theme.Red
import com.technologia.ticketbookingui.ui.theme.Yellow
import kotlinx.coroutines.delay
import java.util.Locale.Category
import kotlin.math.absoluteValue

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

            MarqueeWithClickableText()
//            Text(
//                text = "Book Your Favourite Movie Here!  Book Your Favourite Movie Here!  Book Your Favourite Movie Here!",
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = modifier
//                    .padding(horizontal = 24.dp)
//                    .fillMaxWidth()
//                    .basicMarquee()
//            )

            Spacer(modifier = modifier.height(24.dp))

            Banner()

            Spacer(modifier = modifier.height(24.dp))

            Row (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleMedium
                )

                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "See All")
                }

            }
            Spacer(modifier = modifier.height(4.dp))

            Categories()

            Spacer(modifier = modifier.height(16.dp))

            Row (modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Now Playing Movie",
                    style = MaterialTheme.typography.titleMedium
                )

                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "See All")
                }
            }

            Spacer(modifier = modifier.height(16.dp))

            NowPlayingMovie()

            Spacer(modifier = modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Upcoming Movie",
                    style = MaterialTheme.typography.titleMedium
                )

                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "See All")
                }
            }

            Spacer(modifier = modifier.height(16.dp))

            UpcomingMovie()

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

@Composable
fun MarqueeWithClickableText(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
         append("Hurry Up!! Book Your Favourite Movie Here! with 20% discount.")

         pushStringAnnotation(tag = "Book", annotation = "Book")
         withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)){
             append("Click Here")
         }
         pop()
     }

    Box(modifier = Modifier
        .padding(horizontal = 24.dp)
        .clickable {
            Toast
                .makeText(context, "Clickable Text Clicked!", Toast.LENGTH_SHORT)
                .show()

        }){
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .basicMarquee()

        )
    }

}

@Composable
fun Categories(modifier: Modifier = Modifier) {
    val category = listOf(
        "Action",
        "Romantic",
        "Sci-fi",
        "History",
        "Comedy",
        "Adventure",
        "Horror",
        "Mystery",
        "Thriller",
        "War & Politics"
    )

    val scrollState = rememberScrollState()

    Row (modifier = modifier
        .horizontalScroll(scrollState)
    ){
        repeat(category.size){index->
            Surface(
                modifier = modifier
                    .padding(
                        start = if (index == 0) 24.dp else 0.dp,
                        end = 12.dp
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { }
                    .padding(12.dp)
            ) {
                Text(
                    text = category[index],
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

}

@Preview
@Composable
fun NowPlayingMovie() {
    val pagerState = rememberPagerState(pageCount = { nowPlaying.size })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(
            start = 48.dp,
            end = 48.dp,
            )
    ) {page->
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .graphicsLayer {
                    val pageOffset = pagerState.getOffsetDistanceInPages(page).absoluteValue
                    lerp(
                        start = ScaleFactor(1f, 0.85f),
                        stop = ScaleFactor(1f, 1f),
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale.scaleX
                        scaleY = scale.scaleY
                    }
                }
                .clickable {

                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomCenter
            ){
                Image(
                    painter = painterResource(id = nowPlaying[page].assetImage) ,
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(340.dp)
                )

                Box(modifier = Modifier
                    .fillMaxWidth(fraction = 0.85f)
                    .wrapContentHeight()
                    .graphicsLayer {
                        val pageOffset = pagerState.getOffsetDistanceInPages(page).absoluteValue
                        val translation = pageOffset.coerceIn(0f, 1f)
                        translationY = translation * 200
                    }
                    .background(BlueVariant)
                    .padding(vertical = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ){  
                    Text(
                        text = nowPlaying[page].title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Yellow
                    )
                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = "Buy Ticket",
//                    style = MaterialTheme.typography.titleSmall,
//                    color = Red,
//                    fontWeight = FontWeight.Bold
//                )
            }
        }
    }
}

@Composable
fun UpcomingMovie(modifier: Modifier = Modifier) {
    LazyRow (
        contentPadding = PaddingValues(start = 24.dp)
    ){
        items(count = upcomingMovie.size){index->
            Box(modifier = Modifier
                .padding(end = 24.dp)
                .clickable {  }
            ){
                Column (
                    modifier = modifier
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(8.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = upcomingMovie[index].assetImage),
                        contentDescription = "Upcoming Movie",
                        contentScale = ContentScale.FillBounds,
                        modifier = modifier.size(width = 200.dp, height = 260.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = upcomingMovie[index].title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}