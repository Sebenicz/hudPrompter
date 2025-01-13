package com.gmail.strycharz97.hudprompter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(): ViewModel() {

  val helpText = MutableStateFlow("")

  fun loadText() = viewModelScope.launch {
    helpText.emit("Welcome to prompter app, the app that prompts, in main screen button propmter takes you to prompter screen, button settings shows you... you guessed it.., settings screen which in turn allows you to set stuff like text size, taxt color, darkmode, and other fancy stuff")
  }

}