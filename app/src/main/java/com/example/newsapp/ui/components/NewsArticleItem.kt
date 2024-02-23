package com.example.newsapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.data.model.NewsArticle

@Composable
fun NewsArticleItem(
    article: NewsArticle,
    onArticleClick: ()->Unit
) {
    val model = article.multimedia?.get(0)?.url
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable{
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
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 15.sp
                )
            }

        }

    }
}