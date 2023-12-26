package com.ridery.here.mainModule.model

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.ridery.here.HereApplication
import com.ridery.here.common.dataAccess.UserDao
import com.ridery.here.common.entities.UserEntity
import com.ridery.here.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeDatabase {
    private val dao: UserDao by lazy { HereApplication.database.userDao() }

    suspend fun existsUser() = dao.existsUser()

    suspend fun updateUser(userEntity: UserEntity) = withContext(Dispatchers.IO){
        try {
            dao.updateUser(userEntity)
            Log.d("PRUEBA", "disque agarro")
        } catch (e: Exception){
            Log.d("PRUEBA", "exceptuado, mi paneichon")
            (e as? SQLiteConstraintException)?.let { throw Exception(Constants.ERROR_EXIST) }
            throw Exception(e.message ?: Constants.ERROR_UNKNOW)
        }
    }
}