package com.ridery.here.common.dataAccess

import com.ridery.here.common.entities.ContentEntity
import com.ridery.here.common.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET(Constants.POSTS_PATH)
    suspend fun getUserUpdate(
        @Query(Constants.NAME_PARAM) name: String,
        @Query(Constants.LAST_NAME_PARAM) lastName: String,
        @Query(Constants.EMAIL_PARAM) email: String,
        @Query(Constants.PASSWORD_PARAM) password: String,
        @Query(Constants.PHONE_PARAM) phone: String,
    ) : List<ContentEntity>
}