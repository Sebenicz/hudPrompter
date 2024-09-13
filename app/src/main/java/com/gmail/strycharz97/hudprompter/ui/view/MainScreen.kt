package com.gmail.strycharz97.hudprompter.ui.view

import androidx.compose.runtime.Composable
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
  PrompterScreen() {
    //temporary
  }
}

sealed class Screen(val route: String) {
  data object Main: Screen("main")
  data object Prompter: Screen("prompter")
}