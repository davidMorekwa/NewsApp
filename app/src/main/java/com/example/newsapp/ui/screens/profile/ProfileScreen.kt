package com.example.newsapp.ui.screens.profile

import android.annotation.SuppressLint
import android.app.Application
import android.widget.ToggleButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.R
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.data.utils.Constants.STORED_THEME
import com.example.newsapp.ui.activities.dataStore
import kotlinx.coroutines.flow.map


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileScreenViewModel: ProfileScreenViewModel,
) {
    var isDisplayPreferencesClicked: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var useDarkTheme = LocalContext.current.dataStore.data.map { value: Preferences ->
        return@map value[STORED_THEME]
    }
    val isDarkTheme = useDarkTheme.collectAsState(initial = false)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.height(40.dp)
            )
        }
    ) {
        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 40.dp, end = 16.dp, start = 16.dp)
                .fillMaxSize()
        ) {
            // username and icon
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_test_account_96___),
                    contentDescription = "User Icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Name",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            // Account settings
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Account Settings",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_test_account_96___),
                            contentDescription = "User Icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Column {
                            Text(
                                text = "Your Profile",
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Edit and view profile",
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "More")
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
            // App settings
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "App Settings",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(26.dp))
                // display preferences
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .clickable {
                                isDisplayPreferencesClicked = !isDisplayPreferencesClicked

                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_moon_symbol_90___),
                                contentDescription = "User Icon",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                            Column {
                                Text(
                                    text = "Display Preferences",
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Dark / Light theme",
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                        Icon(imageVector = if(isDisplayPreferencesClicked)Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight, contentDescription = "More")
                    }
                    AnimatedVisibility(
                        visible = isDisplayPreferencesClicked,
                        enter = slideInVertically(animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessHigh))
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 61.dp)
                        ) {
                            Switch(
                                checked = isDarkTheme.value ?: false,
                                onCheckedChange = {
                                    profileScreenViewModel.changeTheme()
                                },
                            )
                        }

                    }
                }
                
                Spacer(modifier = Modifier.height(26.dp))
                // notification
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_notification_96___),
                            contentDescription = "Notification Settings",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Column {
                            Text(
                                text = "Notification",
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Turn notifications on / off",
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "More")
                }
                Spacer(modifier = Modifier.height(26.dp))
                // language
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_language_90___),
                            contentDescription = "User Icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Column {
                            Text(
                                text = "Language",
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Change language preferences",
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "More")
                }
            }

        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    NewsAppTheme(
        useDarkTheme = true
    ) {
//        ProfileScreen()
    }
}