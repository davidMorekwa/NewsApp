package com.example.newsapp.ui.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ViewModel() {
    private var _categories: MutableStateFlow<List<NewsCategoryItem>> = MutableStateFlow(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = remoteRepository.getCatgories()
        }
    }
}