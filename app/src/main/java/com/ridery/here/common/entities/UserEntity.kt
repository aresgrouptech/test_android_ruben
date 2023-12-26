package com.ridery.here.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity", indices = [Index(value = ["id"], unique = true)])
data class UserEntity(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                      var name: String = "",
                      var lastName: String = "",
                      var email: String = "",
                      var password: String = "",
                      var phone: String = "",
                      var isLogged: Boolean = false)
