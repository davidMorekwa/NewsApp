package com.example.newsapp.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle

@Composable
fun ArticleContent(
    article: NewsArticle,
    duration: String,
    dropDownMenuExpanded: Boolean,
    onShowDropDownMenu: ()->Unit,
    onGloballyPositioned:(size: Size) -> Unit,
    dropDownMenuOnDismissRequest:()->Unit,
    buttonSize: Size,
    isMultimediaNull: Boolean,
    onAddToBookmarksClick:()->Unit,
    onAddToFavoritesClick: ()-> Unit,
    onGeminisClick: ()->Unit
) {

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = article.title,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
            fontSize = 13.sp,
        )
        if (isMultimediaNull) {
            Text(
                text = article.articleabstract,
                fontSize = 12.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = duration,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
            )
            IconButton(
                onClick = { onShowDropDownMenu() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_dots_45___),
                    contentDescription = "More",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            onGloballyPositioned(coordinates.size.toSize())
                        }
                )
                MyDropDownMenu(
                    buttonSize = buttonSize,
                    dropDownMenuExpanded = dropDownMenuExpanded,
                    dropDownMenuOnDismissRequest = { dropDownMenuOnDismissRequest() },
                    onAddToBookmarkClick = { onAddToBookmarksClick() },
                    onAddToFavoriteClick = { onAddToFavoritesClick() },
                    onGeminisClick = { onGeminisClick() }
                )

            }
        }
    }
}