package com.example.newsapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun MyDropDownMenu(
    buttonSize: Size,
    dropDownMenuExpanded: Boolean,
    dropDownMenuOnDismissRequest: ()->Unit,
    onAddToBookmarkClick: ()->Unit,
    onAddToFavoriteClick: ()->Unit,
    onGeminisClick: ()->Unit
) {
    val context = LocalContext.current
    var isBookmarkClick by remember {
        mutableStateOf(false)
    }
    var isFavoriteClick by remember {
        mutableStateOf(false)
    }
    val bookmarkIcon = if(!isBookmarkClick) R.drawable.icons8_outlined_bookmark_50___ else R.drawable.icons8_bookmark_50___
    val favoriteIcon = if(!isFavoriteClick) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite
    DropdownMenu(
        expanded = dropDownMenuExpanded,
        onDismissRequest = { dropDownMenuOnDismissRequest() },
        offset = DpOffset(
            x = -buttonSize.width.dp,
            y = (-buttonSize.height.dp)
        ),
        properties = PopupProperties(focusable = true),
    ) {
//        Surface {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        painterResource(id = bookmarkIcon),
                        contentDescription = "Bookmark"
                    )
                },
                text = {
                    Text(
                        text = "Bookmark",
                        fontFamily = FontFamily.Serif
                    )
                },
                onClick = {
                    isBookmarkClick = !isBookmarkClick
                    onAddToBookmarkClick()
                },
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = "Favorite"
                    )
                },
                text = {
                    Text(
                        text = "Favorite",
                        fontFamily = FontFamily.Serif
                    )
                },
                onClick = {
                    isFavoriteClick = !isFavoriteClick
                    onAddToFavoriteClick()
                    Toast.makeText(
                        context,
                        "Added to Favorites!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Ask Gemini",
                        fontFamily = FontFamily.Serif
                    )
                },
                onClick = {
                    onGeminisClick()
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.google_gemini_icon),
                        contentDescription = "Gemini",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )
//        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyDropDownMenuPreview() {
    NewsAppTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
//            MyDropDownMenu(
//            buttonSize = Size.w,
//                dropDownMenuExpanded = true,
//                dropDownMenuOnDismissRequest = { /*TODO*/ },
//                onAddToBookmarkClick = { /*TODO*/ }) {
//
//            }
        }
    }
}