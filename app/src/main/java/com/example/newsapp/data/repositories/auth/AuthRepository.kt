package com.example.newsapp.data.repositories.auth

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String) : Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String, name: String) : Flow<Resource<AuthResult>>
    fun logOut()
}