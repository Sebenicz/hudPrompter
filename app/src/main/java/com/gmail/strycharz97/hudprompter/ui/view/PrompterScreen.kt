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

@Composable
fun PrompterScreen(viewModel: PrompterViewModel = hiltViewModel(), navigateBack: () -> Unit){
  val scrollState = rememberScrollState()
  val currentLine by viewModel.currentLine.collectAsState()
  val content by viewModel.content.collectAsState(initial = "")
  // State to hold line positions
  var linePositions by remember { mutableStateOf(emptyList<Int>()) }
  var viewportHeight by remember { mutableIntStateOf(0) }
  val topPaddingPX = with(LocalDensity.current) { 24.dp.toPx().toInt() }
  var textLayout by remember { mutableStateOf<TextLayoutResult?>(null) }

    //Updates last visibile index in viewModel when scrolling
  LaunchedEffect(scrollState) {
    snapshotFlow { scrollState.value }
      .collect { scrollPosition ->
        textLayout?.let {
          viewModel.updateLastVisibleLine(
            getLastVisibleLineIndex(
              it,
              scrollPosition,
              viewportHeight
            )
          )
        }
      }
  }

  Box(
    Modifier
      .fillMaxSize()
      .onSizeChanged { viewportHeight = it.height - topPaddingPX }) {
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

private fun getLastVisibleLineIndex(
  textLayoutResult: TextLayoutResult,
  scrollOffset: Int,
  viewportHeight: Int
): Int {
  // The visible range is from scrollOffset to (scrollOffset + viewportHeight)
  val visibleEnd = scrollOffset + viewportHeight
  // Find the last line where the line's top is less than visibleEnd
  return List(textLayoutResult.lineCount) { index ->
    textLayoutResult.getLineTop(index)
  }.indexOfLast { lineTop ->
    lineTop < visibleEnd
  }.coerceAtLeast(0) // Ensure at least 0
}
