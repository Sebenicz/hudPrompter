package com.gmail.strycharz97.hudprompter.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.strycharz97.hudprompter.ui.theme.HudprompterTheme
import com.gmail.strycharz97.hudprompter.viewmodel.HelpViewModel

@Composable
fun HelpScreen(viewModel: HelpViewModel = hiltViewModel(), navigateBack: () -> Unit) {

  val helpTextState by viewModel.helpText.collectAsState()

  HelpScreenContent(
    helpText = helpTextState,
    loadText = viewModel::loadText
  )
}

@Composable
fun HelpScreenContent(helpText: String, loadText: () -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceAround
  ){

    HelpTextOrInfo(helpText)

    Button(onClick = loadText) {
      Text("Button to load help text")
    }
  }
}

// this composable displays help text if it is not empty, otherwise it displays a message how to load it
@Composable
fun HelpTextOrInfo(helpText: String) {
  if (helpText.isNotEmpty()) {
    Text(text = helpText)
  } else {
    Text("To load help text, press the button below")
  }
}

@Preview
@Composable
fun HelpScreenPreview() {
  HudprompterTheme {
    Surface {
      HelpScreenContent(helpText = "This is help text", loadText = {})
    }
  }

}

