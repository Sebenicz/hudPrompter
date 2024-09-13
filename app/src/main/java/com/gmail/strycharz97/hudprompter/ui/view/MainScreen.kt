package com.gmail.strycharz97.hudprompter.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainGraph() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.Main.route ) {
    composable(Screen.Main.route) {
      MainScreen() { navController.navigate(it) }
    }
    composable(Screen.Prompter.route) {
      PrompterScreen() { navController.popBackStack() }
    }
  }
}

@Composable
fun MainScreen(navigate: (String) -> Unit){
  Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
    Button(onClick = { navigate(Screen.Prompter.route)}) {
      Text(text = "start")
    }
  }
}

sealed class Screen(val route: String) {
  data object Main: Screen("main")
  data object Prompter: Screen("prompter")
}