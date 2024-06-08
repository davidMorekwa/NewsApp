package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.ui.screens.home.headlines.shimmerLoadingAnimation

@Composable
fun MySubComposeImage(
    model: Multimedia?
) {
    SubcomposeAsyncImage(
        model = model?.url,
        contentDescription = "Article Multimedia",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(model?.height?.dp ?: 220.dp)
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmerLoadingAnimation()
            ) {
            }

        } else {
            SubcomposeAsyncImageContent()
        }
    }
}