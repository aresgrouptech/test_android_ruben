package com.ridery.here.common.dataAccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ridery.here.common.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun existsUser(): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE email = :email AND password = :password")
    suspend fun consultUser(email: String, password: String): UserEntity?

    @Insert
    suspend fun addUser(userEntity: UserEntity): Long

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}