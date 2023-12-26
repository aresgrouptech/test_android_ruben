package com.ridery.here

import android.app.Application
import androidx.room.Room
import com.ridery.here.common.dataAccess.HereDatabase

class HereApplication : Application() {
    companion object{
        lateinit var database: HereDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            HereDatabase::class.java,
            "HereDatabase")
            .build()
    }
}