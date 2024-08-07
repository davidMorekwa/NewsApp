package com.example.newsapp.ui.screens.auth

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.activities.HomeActivity
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(factory = ViewModelProvider.factory)
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val logInState = authViewModel.logInState.collectAsState(initial = null)
    var isInvalidCredentials by rememberSaveable {
        mutableStateOf(false)
    }
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels_cottonbro_3944377),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Background Image",
            modifier = modifier
                .fillMaxSize(1f)
                .blur(radius = 5.dp)
        )
        Surface(
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Login",
                    letterSpacing = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        errorLabelColor = Color.Red,
                        placeholderColor = Color.Gray
                    ),
                    isError = isInvalidCredentials,
                    label = {
                        Text(
                            text = "Email Address",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    },
                    placeholder = {
                        Text(
                            text = "example@gmail.com",
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif
                        )
                    },
                    onValueChange = {
                        email = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        errorLabelColor = Color.Red,
                        placeholderColor = Color.Gray
                    ),
                    isError = isInvalidCredentials,
                    label = {
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    },
                    onValueChange = {
                        password = it
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation() ,
                    shape = RoundedCornerShape(15.dp),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Text(text = "show")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                AnimatedVisibility(
                    visible = isInvalidCredentials,
                    enter = slideInVertically(
                        spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    androidx.compose.material3.Text(
                        text = "Invalid credentials!",
                        fontSize = 15.sp,
                        color = Color.Red,
                        fontFamily = FontFamily.Serif
                    )
                }
                Button(
                    onClick = {
                        authViewModel.loginUser(email, password)
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White,
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Submit", fontFamily = FontFamily.Serif)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            navHostController.navigate(NavigationScreens.REGISTRATION_SCREEN.name)
                        },
                        shape = RoundedCornerShape(15.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            focusedElevation = 0.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Black,
                        ),
                        interactionSource = remember { NoRippleInteractionSource() },
                        modifier = Modifier
                            .border(0.dp, Color.Transparent)
                            .buttonEffect()
                    ) {
                        Text(text = "Register", fontFamily = FontFamily.Serif)
                    }
                    Button(
                        onClick = {

                        },
                        shape = RoundedCornerShape(15.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            focusedElevation = 0.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Black,
                        ),
                        interactionSource = remember { NoRippleInteractionSource() },

                        modifier = Modifier
                            .border(0.dp, Color.Transparent)
                            .buttonEffect()
                    ) {
                        Text(text = "Forgot Password", fontFamily = FontFamily.Serif)
                    }
                }
                if (logInState.value?.isLoading == true) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        BallPulseSyncIndicator()
                    }
                }
            }
        }
        LaunchedEffect(key1 = logInState.value?.isSuccess) {
            scope.launch {
                if (logInState.value?.isSuccess?.isNotEmpty() == true) {
                    val success = logInState.value?.isSuccess
                    Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                    println("SUCCESS MESSAGE $success")
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
            }
        }
        LaunchedEffect(key1 = logInState.value?.isError) {
            scope.launch {
                if (logInState.value?.isError?.isNotEmpty() == true) {
                    val error = logInState.value?.isError
                    Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    println("Error MESSAGE $error")
                    isInvalidCredentials = true
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    NewsAppTheme {
        LoginScreen(navHostController = rememberNavController(), authViewModel = viewModel(factory = ViewModelProvider.factory))
    }
}