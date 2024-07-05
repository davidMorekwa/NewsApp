package com.example.newsapp.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlin.math.absoluteValue


@SuppressLint("Range")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineNewsArticle(newsArticles: List<NewsArticle>) {
    val pagerState = rememberPagerState(
        pageCount = {
            newsArticles.size
        }
    )
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )
    var isCardDeck by remember {
        mutableStateOf(true)
    }
    Column {

        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(320.dp),
            contentPadding = PaddingValues(2.dp),
            pageSpacing = 2.dp,
            flingBehavior = fling,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) { page ->
            val article = newsArticles[page]
            val height = article.multimedia?.get(1)?.height
            val width = article.multimedia?.get(1)?.width
            Card(
                elevation = 20.dp,
                backgroundColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .height(200.dp)
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false,
                    )
//                    .border(2.dp, Color.Blue)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.9f,
                            stop = 1f,
                            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                        )
                        cameraDistance = 1 * density
                        rotationY = lerp(
                            start = 0f,
                            stop = 0f,
                            fraction = pageOffset.coerceIn(-1f, 1f),
                        )
                        lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                    }
            ) {
                if (newsArticles.isNotEmpty()) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(article.multimedia?.get(0)?.url)
                            .size(Size.ORIGINAL)
                            .build()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(article.title.isNotEmpty()) {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = article.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    maxLines = 2,
                                    fontFamily = FontFamily.Serif,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        } else {
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,

                                ) {
                                    Text(
                                        text = "Show More",
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
                                        fontSize = 25.sp
                                    )
                                    Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = "Arrow Right")
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

fun createMutableBitmap(bitmap: Bitmap): Bitmap {
    return bitmap.copy(Bitmap.Config.ARGB_8888, true)
}

fun getDominantSwatch(palette: Palette): Color {
    val dominantSwatch = palette.dominantSwatch
    return dominantSwatch?.rgb?.let { Color(it) } ?: Color.Blue
}
fun getDarkMutedSwatch(palette: Palette): Color {
    val swatch = palette.darkMutedSwatch
    return swatch?.rgb?.let { Color(it) } ?: Color.Blue
}
fun getLightMutedSwatch(palette: Palette): Color {
    val swatch = palette.lightMutedSwatch
    return swatch?.rgb?.let { Color(it) } ?: Color.Blue
}
fun getDarkVibrantSwatch(palette: Palette): Color {
    val swatch = palette.darkVibrantSwatch
    return swatch?.rgb?.let { Color(it) } ?: Color.Black
}
fun getVibrantSwatch(palette: Palette): Color {
    val swatch = palette.vibrantSwatch
    return swatch?.rgb?.let { Color(it) } ?: Color.Blue
}
fun getMutedSwatch(palette: Palette): Color {
    val swatch = palette.mutedSwatch
    return swatch?.rgb?.let { Color(it) } ?: Color.Blue
}


@Preview
@Composable
fun HeadlineNewsArticlePreview() {
    NewsAppTheme {
        HeadlineNewsArticle(Constants.listOfNewsarticles)
    }
}