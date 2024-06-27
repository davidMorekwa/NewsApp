package com.example.newsapp.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.data.utils.Constants.STORED_THEME
import com.example.newsapp.ui.activities.dataStore
import com.example.newsapp.ui.activities.onBoardingDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

const val TAG = "PROFILE SCREEN VIEWMODEL"
class ProfileScreenViewModel(
    private val context:Context,
    private val localRepository: LocalRepository
):ViewModel() {
    val _useDarkTheme: MutableStateFlow<Boolean> = MutableStateFlow(false)
    init {
        this.context.dataStore.data.map { value: Preferences ->
            val currentTheme = value[STORED_THEME]
            _useDarkTheme.value = currentTheme ?: false
        }
    }

    fun changeTheme(){
        viewModelScope.launch(Dispatchers.Main) {
            this@ProfileScreenViewModel.context.dataStore.edit { settings ->
                _useDarkTheme.value = !_useDarkTheme.value
                settings[STORED_THEME] = _useDarkTheme.value
                Log.i(TAG, "Current theme: ${_useDarkTheme.value}")
            }
        }
    }
    fun clearCategories(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.clearCategories()
            context.onBoardingDataStore.edit { value ->
                value[Constants.IS_ONBOARDING_COMPLETE] = false
            }
        }
    }
}