package com.ridery.here.startModule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridery.here.startModule.model.StartRepository
import com.ridery.here.startModule.model.StartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {
    private val repository = StartRepository()

    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    init {
        consultUser()
    }

    private fun consultUser(){
        viewModelScope.launch {
            val hasUser = !repository.existsUser()?.name.isNullOrEmpty()
            val user = repository.existsUser()
            _uiState.update { it.copy(user = user , userExists = hasUser) }
        }
    }
}