package com.example.newsapp.data.repositories.auth

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

const val TAG = "AUTH REPOSITORY"
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
):AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            Log.d(TAG, "Logging in User")
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "${result.user}")
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String, name: String): Flow<Resource<AuthResult>> {
        return flow {
            Log.d(TAG, "Registering User")
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Log.d(TAG, "${result.user}")
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
    override fun logOut() {
        firebaseAuth.signOut()
    }
}