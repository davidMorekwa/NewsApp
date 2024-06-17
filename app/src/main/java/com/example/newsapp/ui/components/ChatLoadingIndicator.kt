package com.example.newsapp.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.delay

@Composable
fun BallPulseSyncIndicator(
    color: Color = if(isSystemInDarkTheme()) Color.White else Color.Black.copy(alpha = 0.8f),
    delay: Long = 90L,
    spaceBetweenBalls: Float = 15f,
    ballDiameter: Float = 10f,
    animationDuration: Int = 350
) {

    val ballCount = 3

    val positions = (1..ballCount).map { index ->

        var animatedValue: Float by remember { mutableStateOf(0f) }

        LaunchedEffect(key1 = Unit) {
            delay(delay + delay * index)

            animate(
                initialValue = 0f, targetValue = 50f, animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = animationDuration),
                    repeatMode = RepeatMode.Reverse,
                )
            ) { value, _ -> animatedValue = value }
        }

        animatedValue
    }

    Canvas(modifier = Modifier) {

        val center = Offset(0f, 0f)
        val xOffset = ballDiameter + spaceBetweenBalls

        for (index in 0 until ballCount) {
            drawCircle(
                color = color, radius = ballDiameter / 2, center = Offset(
                    x = when {
                        index < ballCount / 2 -> -(center.x + xOffset)
                        index == ballCount / 2 -> center.x
                        else -> center.x + xOffset
                    },
                    y = center.y - positions[index]
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    NewsAppTheme {
        androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            BallPulseSyncIndicator()
        }

    }
}