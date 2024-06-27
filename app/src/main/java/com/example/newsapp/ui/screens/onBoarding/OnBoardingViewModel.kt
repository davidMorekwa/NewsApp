package com.example.newsapp.ui.screens.onBoarding

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.activities.onBoardingDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

const val TAG = "OnBoardingViewModel"

class OnBoardingViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ViewModel() {
    private var _currentCategories: MutableStateFlow<List<NewsCategoryEntity>> = MutableStateFlow(emptyList())
    val currentCategories: StateFlow<List<NewsCategoryEntity>> = flow{
        emit(_currentCategories.value)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList<NewsCategoryEntity>())
    var selectedCategories:MutableList<NewsCategoryItem> = mutableListOf()
    private var _status: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val status = _status.asStateFlow()
    val categories: StateFlow<List<NewsCategoryItem>> = flow{
        val res = remoteRepository.getCatgories()
        emit(res)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList<NewsCategoryItem>())

    fun addCategory(categoryItem: NewsCategoryItem){
        viewModelScope.launch(Dispatchers.IO) {
            selectedCategories.add(categoryItem)
            _status.value = selectedCategories.size>5
            Log.d(TAG, "status: ${_status.value}")
        }
    }
    fun getCurrentCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            val res = localRepository.getCategories()
            Log.d(TAG, "getCurrentCategories: $res")
            _currentCategories.value = res
        }
    }
    fun finish(){
        viewModelScope.launch(Dispatchers.IO) {
            val selectedCategoryEntities = selectedCategories.map { it -> it.toNewsCategoryEntity() }
            localRepository.addCategory(selectedCategoryEntities)
        }
    }
    fun storeInPreferenceDataStore(context: Context){
        viewModelScope.launch {
            context.onBoardingDataStore.edit{ values ->
                values[Constants.IS_ONBOARDING_COMPLETE] = true
            }
        }
    }
}