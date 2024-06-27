package com.example.newsapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.navigation.NavigationScreens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    authViewModel: AuthViewModel
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var password_confirm by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordError by rememberSaveable {
        mutableStateOf(false)
    }
    var isPasswordConfirm by rememberSaveable {
        mutableStateOf(false)
    }
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    val registerState = authViewModel.registerState.collectAsState(initial = null)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ){
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
                    text = "Register",
                    letterSpacing = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        errorLabelColor = Color.Red,
                        placeholderColor = Color.Gray
                    ),
                    label = {
                        Text(
                            text = "Name",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    placeholder = {
                        Text(
                            text = "John Doe",
                            fontWeight = FontWeight.Light
                        )
                    },
                    onValueChange = {
                        name = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
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
                    label = {
                        Text(
                            text = "Email Address",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    placeholder = {
                        Text(
                            text = "example@gmail.com",
                            fontWeight = FontWeight.Light
                        )
                    },
                    onValueChange = {
                        email = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
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
                    label = {
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    onValueChange = {
                        password = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Text(text = "show")
                        }
                    },
                    isError = isPasswordError
                )
                OutlinedTextField(
                    value = password_confirm,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        errorLabelColor = Color.Red,
                        placeholderColor = Color.Gray
                    ),
                    isError = isPasswordError,
                    label = {
                        Text(
                            text = "Confirm Password",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    onValueChange = {
                        password_confirm = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            isPasswordError = password != password_confirm
                            authViewModel.registerUser(email, password, name)
                        }
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Text(text = "show")
                        }
                    },
                )
                AnimatedVisibility(
                    visible = isPasswordConfirm,
                    enter = slideInVertically(
                        spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    androidx.compose.material3.Text(
                        text = "Passwords don't match!",
                        fontSize = 15.sp,
                        color = Color.Red
                    )
                }
                Button(
                    onClick = {
                        isPasswordError = password != password_confirm
                        authViewModel.registerUser(email, password, name)
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White,
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Submit")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            navHostController.navigate(NavigationScreens.LOGIN_SCREEN.name)
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
//                            .buttonEffect()
                    ) {
                        Text(text = "Login")
                    }
                }
                if (registerState.value?.isLoading == true) {
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
        LaunchedEffect(key1 = registerState.value?.isSuccess) {
            scope.launch {
                if (registerState.value?.isSuccess?.isNotEmpty() == true) {
                    val success = registerState.value?.isSuccess
                    Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                    println("SUCCESS MESSAGE $success")
                    navHostController.navigate(NavigationScreens.ON_BOARDING_SCREEN.name)
//                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
            }
        }
        LaunchedEffect(key1 = registerState.value?.isError) {
            scope.launch {
                if (registerState.value?.isError?.isNotEmpty() == true) {
                    val error = registerState.value?.isError
                    Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    println("Error MESSAGE $error")
                }
            }
        }
    }
}
enum class ButtonState { Pressed, Idle }
fun Modifier.buttonEffect() = composed{
    var buttonState by remember {
        mutableStateOf(ButtonState.Idle)
    }
    val scale by animateFloatAsState(targetValue = if (buttonState == ButtonState.Pressed) 0.07f else 1f,
        label = "Button Effect",
        animationSpec = tween(500, easing = EaseInOutQuart)
    )
    this
        .graphicsLayer {
            scaleY = scale
            scaleX = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}
class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}