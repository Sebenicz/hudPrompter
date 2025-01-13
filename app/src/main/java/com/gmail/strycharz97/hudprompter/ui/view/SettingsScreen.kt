package com.gmail.strycharz97.hudprompter.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.strycharz97.hudprompter.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel(), navigateBack: () -> Unit) {
  Text("settings screen") //TODO: remove this line, implement screen
}