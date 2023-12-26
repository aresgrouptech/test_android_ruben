package com.ridery.here.mainModule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridery.here.common.entities.UserEntity
import com.ridery.here.mainModule.model.HomeRepository
import com.ridery.here.mainModule.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    private val repository = HomeRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun searchUser(){
        viewModelScope.launch {
            val user = repository.existsUser()
            uiState.value.user!!.id = user!!.id
            uiState.value.user!!.isLogged = true
            logUser(uiState.value.user!!)
        }
    }

    private fun logUser(user: UserEntity){
        var queryElements = 0
        viewModelScope.launch {
            try {
                repository.updateUser(user)
                val result = repository.getUserUpdate(user.name, user.lastName, user.email,
                                                        user.password, user.phone)
                queryElements = result.size
            } catch (e: Exception) {
//                _uiState.value.loginError = getMsgErrorByCode(e.message)
            } finally {
                _uiState.update { it.copy(userUpdated = true, queryElements = queryElements) }
            }
        }
    }

    fun changeIcon(icon: Int) = _uiState.update { it.copy(selectedIcon = icon) }
}