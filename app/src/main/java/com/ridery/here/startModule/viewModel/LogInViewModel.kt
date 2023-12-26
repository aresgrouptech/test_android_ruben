package com.ridery.here.startModule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridery.here.common.entities.UserEntity
import com.ridery.here.common.utils.Constants
import com.ridery.here.common.utils.getMsgErrorByCode
import com.ridery.here.startModule.model.StartRepository
import com.ridery.here.startModule.model.StartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    private val repository = StartRepository()

    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    fun searchUser(){
        viewModelScope.launch {
            val user = repository.consultUser(uiState.value.user!!.email, uiState.value.user!!.password)
            if (user != null) logUser(user)
            else _uiState.update { it.copy(loginError = getMsgErrorByCode(Constants.ERROR_VALIDATE)) }
        }
    }

    private fun logUser(user: UserEntity){
        viewModelScope.launch {
            try {
                user.isLogged = true
                repository.updateUser(user)
                _uiState.update { it.copy(user = user) }
            } catch (e: Exception) {
                _uiState.value.loginError = getMsgErrorByCode(e.message)
            }
        }
    }
}