package com.ridery.here.startModule.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridery.here.startModule.model.StartRepository
import com.ridery.here.startModule.model.StartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val repository = StartRepository()

    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    fun consultUser(){
        viewModelScope.launch {
            val hasUser = !repository.existsUser()?.name.isNullOrEmpty()
            _uiState.update { it.copy(userExists = hasUser) }
        }
    }

    fun saveUser(){
        if (uiState.value.user!!.name.isEmpty() || uiState.value.user!!.lastName.isEmpty()
            || uiState.value.user!!.email.isEmpty() || uiState.value.user!!.password.isEmpty()
            || uiState.value.user!!.phone.isEmpty()) {
            _uiState.update { it.copy(emptyField = true) }
        } else {
            viewModelScope.launch {
                try {
                    repository.saveUser(uiState.value.user!!)
                    consultUser()
                } catch (e: Exception) {
                    Log.d("TEST", "mipana, fallo")
                }
            }
        }
    }
}