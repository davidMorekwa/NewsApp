package com.example.newsapp.ui.screens.onBoarding

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.ui.activities.HomeActivity
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.theme.NewsAppTheme

/*
This is the page that is displayed after the user successfully register.
The user is supposed to select their preferred categories
 */


@Composable
fun OnBoardingScreen(
    onBoardingViewModel: OnBoardingViewModel,
) {
    val context = LocalContext.current
    var status = onBoardingViewModel.status.collectAsState()
    var categories = onBoardingViewModel.categories.collectAsState().value
    if(categories.isEmpty()){
        Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()){
            BallPulseSyncIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(bottom = 30.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ){
                Text(
                    text = "Select at least 5 categories",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            items(categories) { category: NewsCategoryItem ->
                MyButton(
                    categoryItem = category,
                    onCategoryClick = {
                        onBoardingViewModel.addCategory(categoryItem = category)
                    }
                )
            }
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                ElevatedButton(
                    onClick = {
                        onBoardingViewModel.finish()
                        onBoardingViewModel.storeInPreferenceDataStore(context = context)
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray
                    ),
                    enabled = status.value,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                ) {
                    Text(
                        text = "COMPLETE",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 10.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}

@Composable
fun MyButton(
    categoryItem: NewsCategoryItem,
    onCategoryClick:()->Unit
) {
    var isClicked by rememberSaveable {
        mutableStateOf(false)
    }
    ElevatedButton(
        onClick = {
            isClicked = !isClicked
            onCategoryClick()
        },
        contentPadding = PaddingValues(horizontal = 16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = categoryItem.name,
                fontFamily = FontFamily.Serif,
            )
            AnimatedContent(
                targetState = isClicked,
                transitionSpec = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow,
                        )
                    ) togetherWith(
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                animationSpec = tween(300))
                            )
                },
                label = "Animation"
            ) { targetState ->
                Icon(
                    imageVector = if (targetState) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = "Add",
                    tint = if (targetState) Color.Green else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Preview()
@Composable
fun OnBoardingScreenPreviewDark() {
    NewsAppTheme {
//        OnBoardingScreen()
    }
}
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreviewLight() {
    NewsAppTheme(useDarkTheme = false) {
//        OnBoardingScreen()
    }
}