package com.example.newsapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, position: Float) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        modifier = Modifier
            .graphicsLayer { translationY = position }
            .height(30.dp)
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    NewsAppTheme {
        TopAppBar(title = "Headlines", position = 0f)
    }
}