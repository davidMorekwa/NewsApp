package com.example.newsapp.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.utils.Constants.sampleArticle
import com.example.newsapp.ui.theme.NewsAppTheme
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Composable
fun NewsArticleItem(
    article: NewsArticle,
    onArticleClick: () -> Unit,
    onAddToFavoriteClick: () -> Unit,
    onAddToBookmarkClick:() -> Unit,
    onGeminisClick: () -> Unit,
) {
    val context = LocalContext.current
    var itemClicked by remember {
        mutableStateOf(false)
    }
    val currentTime = OffsetDateTime.now(ZoneOffset.UTC)
    val published = OffsetDateTime.parse(article.publishedDate)
    val timeDifference = Duration.between(published,currentTime)
    val duration = when {
        timeDifference.toHours() == 0L -> "${timeDifference.toMinutes()} min"
        else -> "${timeDifference.toHours()} hr${if (timeDifference.toHours() > 1) "s" else ""}"
    }
    Log.d("NEws Article Item", "Duration:: ${duration}")

    val multimedia = article.multimedia

    var dropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
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
        if (multimedia.isNullOrEmpty()){
            Column {
                    ArticleContent(
                        article = article,
                        duration = duration,
                        dropDownMenuExpanded = dropDownMenuExpanded,
                        onShowDropDownMenu = { dropDownMenuExpanded = true },
                        onGloballyPositioned = { buttonSize = it },
                        dropDownMenuOnDismissRequest = { dropDownMenuExpanded = false },
                        buttonSize = buttonSize,
                        isMultimediaNull = true,
                        onAddToBookmarksClick = { onAddToBookmarkClick() },
                        onAddToFavoritesClick = { onAddToFavoriteClick() },
                        onGeminisClick = {
                            onGeminisClick()
                            dropDownMenuExpanded = false
                        }
                    )
            }
        } else {
            val model = if(multimedia.size > 3) multimedia.get(2) else multimedia.get(1)
            if (model?.format != "Standard Thumbnail") {
                Column {
                    MySubComposeImage(model = model)
                    ArticleContent(
                        article = article,
                        duration = duration,
                        dropDownMenuExpanded = dropDownMenuExpanded,
                        onShowDropDownMenu = { dropDownMenuExpanded = true },
                        onGloballyPositioned = { buttonSize = it },
                        dropDownMenuOnDismissRequest = { dropDownMenuExpanded = false },
                        buttonSize = buttonSize,
                        isMultimediaNull = true,
                        onAddToBookmarksClick = { onAddToBookmarkClick() },
                        onAddToFavoritesClick = { onAddToFavoriteClick() },
                        onGeminisClick = {
                            onGeminisClick()
                            dropDownMenuExpanded = false
                        }
                    )
                }
            } else {
                Row {
                    MySubComposeImage(model = model)
                    ArticleContent(
                        article = article,
                        duration = duration,
                        dropDownMenuExpanded = dropDownMenuExpanded,
                        onShowDropDownMenu = { dropDownMenuExpanded = true },
                        onGloballyPositioned = { buttonSize = it },
                        dropDownMenuOnDismissRequest = { dropDownMenuExpanded = false },
                        buttonSize = buttonSize,
                        isMultimediaNull = true,
                        onAddToBookmarksClick = { onAddToBookmarkClick() },
                        onAddToFavoritesClick = { onAddToFavoriteClick() },
                        onGeminisClick = {
                            onGeminisClick()
                            dropDownMenuExpanded = false
                        }
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
        NewsArticleItem(
            article = sampleArticle,
            onArticleClick = {},
            onAddToFavoriteClick = {},
            onAddToBookmarkClick = {},
            onGeminisClick = {},
        )
    }
}