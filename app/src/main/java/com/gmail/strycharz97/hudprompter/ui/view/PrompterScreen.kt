package com.gmail.strycharz97.hudprompter.ui.view

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.strycharz97.hudprompter.viewmodel.PrompterViewModel

@Composable
fun PrompterScreen(viewModel: PrompterViewModel = hiltViewModel(), navigateBack: () -> Unit){
  val content = """loooooool
    |second line
    |third line sadjiajklsjndfklasdjfo;ijkopiajfoijioasdjgfoijsaoifj;aojolfjlk;sdjfolkajl;kdfj;lkasdjfdjasoijf
    |fourth line
    |fifth line
    |sixth line
    |seventh line
    |and another line
    |another
    |empty is coming
    |
    |maybe two empty ones
    |
    |
    |woah thats a lot of space
    | 1.how it works with tabs and shit ton of text?
    | 2.who knowssss
    | -ehhhhhh that's a lot of lines, i wonder if this ever gonna work
  """.trimMargin()

  val scrollState = rememberScrollState()
  val currentLine by viewModel.currentLine.collectAsState()

  // State to hold line positions
  var linePositions by remember { mutableStateOf(emptyList<Int>()) }

  Box(Modifier.fillMaxSize()) {
    OutlinedButton(onClick = {viewModel.nextLine() }, modifier = Modifier
      .align(Alignment.TopStart)
      .padding(4.dp)) {
      Icon(Icons.Filled.ArrowBack, null)
    }
    Column(
      Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .verticalScroll(scrollState)
    ){
      Text(
        text = content,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.fillMaxWidth(),
        onTextLayout = { linePositions = getLinePositions(it) }
      )
    }
  }
  LaunchedEffect(currentLine, linePositions) {
    if (linePositions.isNotEmpty() && currentLine in linePositions.indices) {
      val offset = linePositions[currentLine]
      scrollState.animateScrollTo(offset)
    }
  }
}

private fun getLinePositions(textLayoutResult: TextLayoutResult): List<Int> {
  val lineCount = textLayoutResult.lineCount
  val positions = mutableListOf<Int>()
  for (line in 0 until lineCount) {
    val lineTop = textLayoutResult.getLineTop(line)
    positions.add(lineTop.toInt())
  }
  return positions
}
