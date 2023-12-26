package com.ridery.here.common.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ridery.here.common.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class HereDatabase : RoomDatabase(){
   abstract fun userDao(): UserDao
}