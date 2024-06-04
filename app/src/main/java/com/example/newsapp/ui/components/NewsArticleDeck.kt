package com.example.newsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.glance.layout.Column
import coil.compose.AsyncImage
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun DeckOfCards(cards: List<NewsArticle>, onDeckClick: () -> Unit) {
    Box() {
        cards.forEachIndexed { index, card ->
            val offset = (index * 3).dp

            SingleCard(
                article = card,
                onDeckClick = onDeckClick,
                modifier = Modifier
                    .offset(x = -offset)
                    .padding(top = offset / 5, start = 16.dp)
            )
        }
    }
}

@Composable
fun SingleCard(
    article: NewsArticle,
    modifier: Modifier = Modifier,
    onDeckClick:()->Unit
) {
    val height = article.multimedia?.get(1)?.height?.minus(120)
    val width = article.multimedia?.get(1)?.width?.minus(200)
    Card(
        modifier = modifier
            .width(width!!.dp)
            .height(height!!.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                onDeckClick()
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            AsyncImage(
                model = article.multimedia?.get(0)?.url,
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            )
            Text(text = article.title)
        }
    }
}


@Preview
@Composable
fun DeckOfCardsPrview() {
    NewsAppTheme {
        DeckOfCards(
            cards = Constants.listOfNewsarticles,
            onDeckClick = {}
        )
    }
}