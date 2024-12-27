package com.gmail.strycharz97.hudprompter.ui.view

import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.strycharz97.hudprompter.viewmodel.PrompterViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PrompterScreen(viewModel: PrompterViewModel = hiltViewModel(), navigateBack: () -> Unit){
  val scrollState = rememberScrollState()
  val currentLine by viewModel.currentLine.collectAsState()
  val content by viewModel.content.collectAsState(initial = "")
  var linePositions by remember { mutableStateOf(emptyList<Int>()) }
  var viewportHeight by remember { mutableIntStateOf(0) }
  val topPaddingPX = with(LocalDensity.current) { 24.dp.toPx().toInt() } //TODO: padding
  var textLayout by remember { mutableStateOf<TextLayoutResult?>(null) }

  Box(
    Modifier
      .fillMaxSize()
      .onSizeChanged { viewportHeight = it.height - topPaddingPX }) {
    OutlinedButton(onClick = navigateBack, modifier = Modifier
      .align(Alignment.TopStart)
      .padding(4.dp)) {
      Icon(Icons.Filled.ArrowBack, null)
    }
    Column(
      Modifier
        .fillMaxWidth()
        .padding(24.dp) //TODO: padding
        .verticalScroll(scrollState)
    ){
      Text(
        text = content,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.fillMaxWidth(),
        onTextLayout = {
          linePositions = getLinePositions(it)
          viewModel.update(getDisplayedLines(content, it))
          textLayout = it
        }
      )
    }
  }
  LaunchedEffect(currentLine, linePositions) {
    if (linePositions.isNotEmpty() && currentLine in linePositions.indices) {
      val offset = linePositions[currentLine]
      scrollState.animateScrollTo(offset)
    }
  }
  LaunchedEffect(scrollState, linePositions) {
    snapshotFlow { scrollState.value }
      .distinctUntilChanged()
      .collect { scrollOffset ->
        val firstVisibleLine = linePositions.indexOfFirst { it >= scrollOffset }
        if (firstVisibleLine >= 0) {
          viewModel.updateFirstVisibleIndex(firstVisibleLine)
        }
      }
  }
}

fun getDisplayedLines(text: String, textLayoutResult: TextLayoutResult): List<String> {
  val lines = mutableListOf<String>()
  for (i in 0 until textLayoutResult.lineCount) {
    val start = textLayoutResult.getLineStart(i)
    val end = textLayoutResult.getLineEnd(i)
    lines.add(text.substring(start, end).trimEnd())
  }
  return lines
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
