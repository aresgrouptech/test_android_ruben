package com.ridery.here.startModule.model

import android.database.sqlite.SQLiteConstraintException
import com.ridery.here.HereApplication
import com.ridery.here.common.dataAccess.UserDao
import com.ridery.here.common.entities.UserEntity
import com.ridery.here.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StartDatabase {
    private val dao: UserDao by lazy { HereApplication.database.userDao() }

    suspend fun existsUser() = dao.existsUser()

    suspend fun consultUser(email: String, password: String) = dao.consultUser(email, password)

    suspend fun saveUser(userEntity: UserEntity) = withContext(Dispatchers.IO){
        try {
            dao.addUser(userEntity)
        } catch (e: Exception){
            (e as? SQLiteConstraintException)?.let { throw Exception(Constants.ERROR_EXIST) }
            throw Exception(e.message ?: Constants.ERROR_UNKNOW)
        }
    }

    suspend fun updateUser(userEntity: UserEntity) = withContext(Dispatchers.IO){
        try {
            dao.updateUser(userEntity)
        } catch (e: Exception){
            (e as? SQLiteConstraintException)?.let { throw Exception(Constants.ERROR_EXIST) }
            throw Exception(e.message ?: Constants.ERROR_UNKNOW)
        }
    }
}