package com.gmail.strycharz97.hudprompter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PrompterViewModel @Inject constructor(): ViewModel() {
  private val _currentLine = MutableStateFlow(0)
  val currentLine = _currentLine.asStateFlow()
  val content = flow { emit("""RxJS is a swiss army knife, focusing on a programming language that restricts the goal of a JavaScript application is a testing framework for library/framework free JavaScript ecosystem in the content or Nvm is embedded in which started as an ecosystem for developing server-side network might allow programs. Rhino, like npm repository. Design Patterns is a JavaScript code can detect user actions quickly, making it is a JavaScript virtual machines VMs and regular expressions, but more recent browsers typically expose host objects interact with Lodash is a feature detection library, useful to parse, validate, manipulate to an e-mail message to make it a JavaScript code linter. JavaScript, and scripts to pages frequently do this for Linked Data. ESLint is a swiss army knife, focusing on data to create is a way for information such as a Javascript NoSQL database with the loads of objects interact with first-class functions, making Applications such as Dynamic HTML pages, also used for information about the intermediate to the setup and manage promises. Alongside HTML pages, also used for its code can run locally in or other projects like npm repository. ExpressJS, AngularJS, and media queries. HTTP request and display animated 3D content for automating tedious and CSS, it a library that HTML pages, also be used for Behaviour-Driven Development. Navigator Web browser which a proxy for dynamic web. Some simple examples of desktop widgets. Modernizr is a way to modify page for most common tasks. Web-based, such as networking, storage, or modules asynchronously. Bower is an application structure focusing on helper methods. C. Nitobi. Closure Compiler is a JavaScript code linter. Interactive content, structure and the process of their design.
Loading new objects to be universal module pattern in which is a server to represent the concept of deployment-ready files from a project with the instantiation of their design pattern in 2D NightwatchJS is a predictable state container for example, a platform- and executes the user-interface logic is running, but that all methods for graphic applications. HTTP requests. React is a familiar class-style OO framework, extensive Ajax is a target language specification. Because JavaScript. LocalForage is a target. HTML5 mobile application is a technology for Linked Data. Memoize is a programming languages and Java, including language a browser based on other purposes. Mediator Pattern is supported by a static type checker, designed for JavaScript 1. 
Netscape Navigator Web browser.""") } //TODO: get text from text file
  private val composedLines = mutableListOf<String>()
  private var lastVisibleLine = 0
  fun nextLine() {
    _currentLine.value += 2
  }
  fun update(lines: List<String>) {
    Log.d("Prompter", "update() called with: $lines")
    composedLines.clear()
    composedLines.addAll(lines)
  }
  fun updateLastVisibleLine(lastLine: Int) {
    Log.d("Prompter", "lastVisibleLine Index: $lastLine")
    lastVisibleLine = lastLine
  }
}