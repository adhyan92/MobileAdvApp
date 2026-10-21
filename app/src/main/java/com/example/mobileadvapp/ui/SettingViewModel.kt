package com.example.mobileadvapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileadvapp.data.local.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch { pref.saveThemeSetting(isDarkModeActive) }
    }

    fun getUserSession(): LiveData<Pair<String, String>> = pref.getUserSession().asLiveData()

    fun saveUserSession(token: String, name: String) {
        viewModelScope.launch { pref.saveUserSession(token, name) }
    }

    fun clearUserSession() {
        viewModelScope.launch { pref.clearUserSession() }
    }
}