package com.ridery.here.startModule.model

import com.ridery.here.common.entities.UserEntity

data class StartUiState (
    var userExists: Boolean = false,
    var user: UserEntity? = UserEntity(),
    var emptyField: Boolean = false,
    var loginError: Int = 0
)