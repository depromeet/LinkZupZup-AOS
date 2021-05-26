package com.depromeet.linkzupzup.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.depromeet.linkzupzup.domains.entities.LinkEntity
import com.depromeet.linkzupzup.presenter.model.LinkData

@Database(version = 1, entities = [LinkEntity::class], exportSchema = false)
abstract class RoomDB : RoomDatabase(){

    abstract fun linkDao() : LinkDao

    companion object{
        private var instance : RoomDB ? = null

        @Synchronized
        fun getInstance(context: Context) : RoomDB{
            if(instance==null){
                instance = Room.databaseBuilder(context.applicationContext, RoomDB::class.java,"linkzupzup_roomdb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        fun destroyInstance(){
            instance = null
        }
    }
}