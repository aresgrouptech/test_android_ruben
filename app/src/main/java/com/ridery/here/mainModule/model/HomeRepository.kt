package com.ridery.here.mainModule.model

import com.ridery.here.common.entities.ContentEntity
import com.ridery.here.common.entities.UserEntity

class HomeRepository {
    private val roomDatabase = HomeDatabase()
    private val remoteDatabase = RemoteDatabase()

    suspend fun existsUser() = roomDatabase.existsUser()

    suspend fun updateUser(user: UserEntity) = roomDatabase.updateUser(user)

    suspend fun getUserUpdate(name: String, lastName: String, email: String,
                              password: String, phone: String) : List<ContentEntity> =
        remoteDatabase.getUserUpdate(name, lastName, email, password, phone)
}