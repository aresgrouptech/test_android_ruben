package com.ridery.here.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity", indices = [Index(value = ["id"], unique = true)])
data class ContentEntity(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                         var userId: Long = 0,
                         var title: String = "",
                         var body: String = "")
