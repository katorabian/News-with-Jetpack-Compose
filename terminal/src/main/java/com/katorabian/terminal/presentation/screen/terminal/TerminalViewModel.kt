package com.katorabian.terminal.presentation.screen.terminal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.terminal.data.net.ApiFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TerminalViewModel: ViewModel() {

    //TODO( Use DI )
    private val apiService = ApiFactory.apiService

    private val _state = MutableStateFlow<TerminalScreenState>(TerminalScreenState.Initial)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            this@TerminalViewModel::class.java.simpleName,
            "Exception caught:\n${Log.getStackTraceString(throwable)}"
        )
    }

    init {
        loadBarList()
    }

    private fun loadBarList() {
        viewModelScope.launch(exceptionHandler) {
            val barList = apiService.loadBars().barList
            _state.value = TerminalScreenState.Content(barList = barList)
        }
    }
}