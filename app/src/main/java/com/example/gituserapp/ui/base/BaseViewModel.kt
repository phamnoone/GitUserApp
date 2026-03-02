package com.example.gituserapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, I : Input, E : Output> : ViewModel() {

    private val initialState: UiState<S> by lazy { createInitialState() }
    
    abstract fun createInitialState(): UiState<S>

    private val _uiState: MutableStateFlow<UiState<S>> = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState<S>> = _uiState.asStateFlow()

    private val _output: Channel<E> = Channel()
    val output: Flow<E> = _output.receiveAsFlow()

    abstract fun handleInput(intent: I)

    fun onInput(input: I) {
        handleInput(input)
    }

    protected fun setState(reduce: UiState<S>.() -> UiState<S>) {
        _uiState.value = uiState.value.reduce()
    }

    protected fun emitOutput(builder: () -> E) {
        viewModelScope.launch {
            _output.send(builder())
        }
    }
}
