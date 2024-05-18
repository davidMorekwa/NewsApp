package com.example.newsapp.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsArticleItem(
    article: NewsArticle,
    onArticleClick: ()->Unit
) {
    var itemClicked by remember {
        mutableStateOf(false)
    }
    val model = article.multimedia?.get(0)?.url
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_outlined_bookmark_50___),
                        contentDescription = "Bookmark",
                        tint = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                    )
                }
            }

        }

    }
}



@Preview
@Composable
fun MyPreview() {
    NewsAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text(text = "Click me")
        }
    }
}