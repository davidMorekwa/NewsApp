package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.ui.screens.home.GeminiUiState
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.ai.client.generativeai.type.asTextOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    onDismissRequest: () -> Unit,
    onSendClick: (String) -> Unit,
    sheetState: SheetState,
    chatHistory: GeminiUiState
) {
    var chatText by remember {
        mutableStateOf("")
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        modifier = Modifier
            .padding(bottom = 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_gemini_icon),
                    contentDescription = "Gemini",
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = "GEMINI AI",
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

            Box(
                contentAlignment = if(chatHistory is GeminiUiState.Loading) Alignment.Center else Alignment.TopStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.81f)
            ) {
                when (chatHistory) {
                    is GeminiUiState.Loading -> {
                        BallPulseSyncIndicator()
                    }
                    is GeminiUiState.Success -> {
                        val listState = rememberLazyListState()
                        LaunchedEffect(chatHistory) {
                            if (chatHistory.chatHistory.isNotEmpty()) {
                                listState.animateScrollToItem(chatHistory.chatHistory.size - 1)
                            }
                        }
                        LazyColumn(
                            horizontalAlignment = Alignment.End,
                            state = listState,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            var chats = chatHistory.chatHistory
                            items(chats.size) { index ->
                                var role = chats[index].role
                                Surface(
                                    tonalElevation = if (role == "user") 2.dp else 0.dp,
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(if (role == "user") 0.85f else 1f)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = chats[index].parts.first().asTextOrNull()
                                            ?: "",
                                        textAlign = if (role == "user") TextAlign.End else TextAlign.Start,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            }
                            if(chatHistory.isResponseLoading){
                                item {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        BallPulseSyncIndicator()
                                    }
                                }
                            }
                        }
                    }
                    is GeminiUiState.Error -> {
                        Text(
                            text = chatHistory.message,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 40.dp)
//            ) {
//                OutlinedTextField(
//                    value = chatText,
//                    onValueChange = { chatText = it },
//                    label = {
//                        Text(
//                            "Message",
//                            letterSpacing = 2.sp,
//                            fontFamily = FontFamily.Serif
//                        )
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
//                        unfocusedTextColor = Color.Gray,
//                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
//                    ),
//                    shape = RoundedCornerShape(15.dp),
//                    trailingIcon = {
//                        IconButton(
//                            onClick = {
//                                onSendClick(chatText)
//                                chatText = ""
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.Send,
//                                contentDescription = "Send"
//                            )
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth(0.85f)
//                )
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BottomSheetPreview() {
    var sheetState = rememberModalBottomSheetState()
    NewsAppTheme(useDarkTheme = true) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(color = MaterialTheme.colorScheme.background)) {
        }
    }
}