package com.ridery.here.mainModule.model

import com.ridery.here.common.dataAccess.UserService
import com.ridery.here.common.entities.ContentEntity
import com.ridery.here.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDatabase {
    private val retrofit = Retrofit.Builder()
     .baseUrl(Constants.BASE_URL)
     .addConverterFactory(GsonConverterFactory.create())
     .build()
    private val service = retrofit.create(UserService::class.java)

    suspend fun getUserUpdate(name: String, lastName: String, email: String,
                                password: String, phone: String) : List<ContentEntity> =
        withContext(Dispatchers.IO){
            service.getUserUpdate(name, lastName, email, password, phone)
    }
}