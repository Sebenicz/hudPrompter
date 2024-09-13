package com.gmail.strycharz97.hudprompter.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PrompterViewModel @Inject constructor(): ViewModel() {
  private val _currentLine = MutableStateFlow(0)
  val currentLine = _currentLine.asStateFlow()
  fun nextLine() {
    _currentLine.value += 2
  }
}