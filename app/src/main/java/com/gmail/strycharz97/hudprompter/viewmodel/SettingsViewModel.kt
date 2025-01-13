package com.gmail.strycharz97.hudprompter.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {

  val isDarkMode = MutableStateFlow(false)
  val textSize = MutableStateFlow(16)
  val textColor = MutableStateFlow(1)

  fun increaseTextSize() {
    //TODO: increase text size
  }
  fun decreaseTextSize() {
    //TODO: decrease text size
  }

  fun toggleDarkMode() {
    //TODO: toggle dark mode
  }

  fun increaseTextColor() {
    //TODO: increase text color
  }

  fun decreaseTextColor() {
    //TODO: decrease text color
  }
}