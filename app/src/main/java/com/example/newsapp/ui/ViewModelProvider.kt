package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.NewsApplication
import com.example.newsapp.ui.screens.auth.AuthViewModel
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.profile.ProfileScreenViewModel
import com.example.newsapp.ui.screens.search.SearchScreenViewModel
import com.example.newsapp.ui.screens.webview.WebViewViewModel

object ViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                remoteRepository = newsApplication().appContainer.remoteRepository,
                localRepository = newsApplication().appContainer.localRepository
            )
        }
        initializer {
            WebViewViewModel()
        }
        initializer {
            SearchScreenViewModel(
                remoteRepository = newsApplication().appContainer.remoteRepository,
                localRepository = newsApplication().appContainer.localRepository
            )
        }
        initializer {
            ProfileScreenViewModel(
                context = newsApplication().applicationContext
            )
        }
        initializer {
            AuthViewModel(
                authRepository = newsApplication().appContainer.authRepository
            )
        }
    }


}
fun CreationExtras.newsApplication():NewsApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)