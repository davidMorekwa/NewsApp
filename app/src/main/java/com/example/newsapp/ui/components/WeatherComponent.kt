package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.data.model.response.weather.WeatherResponse

@Composable
fun WeatherComponent(modifier: Modifier = Modifier, weatherResponse: WeatherResponse) {
    val weatherIcon = weatherResponse.weather[0].icon
    Surface(
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { /*TODO*/ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(horizontal = 8.dp),
        ) {
            Text(
                text = "${weatherResponse.main.temp}\u2103",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${weatherIcon}@2x.png",
                contentDescription = "Icon",
                modifier = modifier
                    .size(50.dp)
            )
        }
    }
}