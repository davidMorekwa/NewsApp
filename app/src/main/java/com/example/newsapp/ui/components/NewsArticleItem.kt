package com.example.newsapp.ui.components

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.R
import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsArticleItem(
    article: NewsArticle,
    onArticleClick: () -> Unit,
    onAddToFavoriteClick: () -> Unit,
    onAddToBookmarkClick:() -> Unit
) {
    val context = LocalContext.current
    var itemClicked by remember {
        mutableStateOf(false)
    }
    val model = article.multimedia?.get(0)?.url
    var dropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
    var isBookmarkClick by remember {
        mutableStateOf(false)
    }
    var isFavoriteClick by remember {
        mutableStateOf(false)
    }
    val bookmarkIcon = if(!isBookmarkClick) R.drawable.icons8_outlined_bookmark_50___ else R.drawable.icons8_bookmark_50___
    val favoriteIcon = if(!isFavoriteClick) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite
    var buttonSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable {
                onArticleClick()
            }
    ) {
        Column {
            SubcomposeAsyncImage(
                model = model,
                contentDescription = "Article Multimedia",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
            ) {
                val state = painter.state
                if(state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.outline,
                            strokeWidth = 1.dp,
                            strokeCap = StrokeCap.Round,
                        )
                    }

                } else{
                    SubcomposeAsyncImageContent()
                }
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.articleabstract,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 15.sp
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { dropDownMenuExpanded = true },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_dots_45___),
                            contentDescription = "More",
                            tint = Color.Black,
                            modifier = Modifier
                                .clickable { dropDownMenuExpanded = true }
                                .onGloballyPositioned { coordinates ->
                                    buttonSize = coordinates.size.toSize()
                                }
                        )
                    }

                    DropdownMenu(
                        expanded = dropDownMenuExpanded,
                        onDismissRequest = { dropDownMenuExpanded = false },
                        offset = DpOffset(x = -buttonSize.width.dp, y = (-buttonSize.height.dp)),
                        properties = PopupProperties(focusable = true),
                        modifier = Modifier
                            .background(color = Color.White)
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(painterResource(id = bookmarkIcon), contentDescription ="Bookmark")
                            },
                            text = {
                                Text(text = "Bookmark")
                            },
                            onClick = {
                                isBookmarkClick = !isBookmarkClick
                                onAddToBookmarkClick()
                            }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(imageVector = favoriteIcon, contentDescription ="Favorite")
                            },
                            text = {
                                Text(text = "Favorite")
                            },
                            onClick = {
                                isFavoriteClick = !isFavoriteClick
                                onAddToFavoriteClick()
                                Toast.makeText(context, "Added to Favorites!", Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                }
            }

        }

    }
}

val sampleArticle = NewsArticle(
    articleabstract = "In a groundbreaking discovery, scientists have uncovered a new species of dinosaur that roamed the earth millions of years ago.",
    byline = "By John Doe",
    itemType = "Article",
    multimedia = listOf(
        Multimedia(
            url = "https://example.com/dinosaur.jpg",
            format = "superJumbo",
            height = 1200,
            width = 1600,
            type = "image",
            subtype = "photo",
            caption = "The newly discovered species of dinosaur, believed to be one of the largest ever found.",
            copyright = "sample copyright"
        )
    ),
    publishedDate = "2024-05-24",
    section = "Science",
    subsection = "Paleontology",
    title = "New Species of Dinosaur Discovered",
    nyturi = "nyt://article/12345678-90ab-cdef-1234-567890abcdef",
    htmlurl = "https://www.nytimes.com/2024/05/24/science/new-dinosaur-species.html"
)



@Preview
@Composable
fun MyPreview() {

    NewsAppTheme {
        NewsArticleItem(
            article = sampleArticle,
            onArticleClick = {},
            onAddToFavoriteClick = {},
            onAddToBookmarkClick = {}
        )
    }
}