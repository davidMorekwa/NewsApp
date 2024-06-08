package com.example.newsapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repositories.auth.AuthRepository
import com.example.newsapp.data.repositories.auth.Resource
import com.example.newsapp.data.repositories.auth.SignInState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    val _logInState = Channel<SignInState>()
    var logInState = _logInState.receiveAsFlow()

    val _registerState = Channel<SignInState>()
    var registerState = _registerState.receiveAsFlow()
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            authRepository.loginUser(email, password).collect{result ->
                when (result){
                    is Resource.Success -> {
                        _logInState.send(SignInState(isSuccess = "Sign In Successful"))
                    }
                    is Resource.Loading -> {
                        _logInState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error  -> {
                        _logInState.send(SignInState(isError = result.message))
                        println("SIGNIN ERROR!! ${result.message}")
                    }
                }
            }
        }
    }
    fun registerUser(email: String, password: String, name: String){
        viewModelScope.launch {
            authRepository.registerUser(email, password, name).collect{result ->
                when (result){
                    is Resource.Success -> {
                        _registerState.send(SignInState(isSuccess = "Register Successful"))
                    }
                    is Resource.Loading -> {
                        _registerState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error  -> {
                        _registerState.send(SignInState(isError = result.message))
                    }
                }
            }
        }
    }
    fun logOut(){
        viewModelScope.launch {
            authRepository.logOut()
        }
    }
}