package com.ridery.here.mainModule.model

import com.ridery.here.common.entities.UserEntity

data class HomeUiState (
    var user: UserEntity? = UserEntity(),
    var userUpdated: Boolean = false,
    var queryElements: Int = 0,
    var selectedIcon: Int = 0
)