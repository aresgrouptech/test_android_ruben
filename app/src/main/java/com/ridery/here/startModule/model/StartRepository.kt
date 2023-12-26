package com.ridery.here.startModule.model

import com.ridery.here.common.entities.UserEntity

class StartRepository {
    private val roomDatabase = StartDatabase()

    suspend fun existsUser() = roomDatabase.existsUser()

    suspend fun saveUser(user: UserEntity){
        // TODO: Mover validaciones de campo para aca
        roomDatabase.saveUser(user)
    }

    suspend fun consultUser(email: String, password: String) = roomDatabase.consultUser(email, password)

    suspend fun updateUser(user: UserEntity) = roomDatabase.updateUser(user)
}