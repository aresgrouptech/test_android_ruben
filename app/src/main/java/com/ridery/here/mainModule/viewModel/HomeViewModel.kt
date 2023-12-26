package com.ridery.here.mainModule.viewModel

import androidx.lifecycle.ViewModel
import com.ridery.here.mainModule.model.HomeUiState
import com.ridery.here.startModule.model.StartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val repository = StartRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init{
        consultUser()
    }

    private fun consultUser(){
//        _uiState.value.userExists.let {
//            viewModelScope.launch {
//                _uiState.value.userExists = repository.existsUser() == null
//            }
//        }
    }
}