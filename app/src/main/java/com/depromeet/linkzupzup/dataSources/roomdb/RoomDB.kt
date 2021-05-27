package com.depromeet.linkzupzup.dataSources.roomdb

import android.content.Context
import androidx.room.*
import com.depromeet.linkzupzup.domains.entities.LinkEntity

@Database(version = 1, entities = [LinkEntity::class], exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class RoomDB : RoomDatabase(){

    abstract fun linkDao() : LinkDao

    companion object{
        private var instance : RoomDB ? = null
        private const val DB_NAME = "linkzupzup_roomdb"

        @Synchronized
        fun getInstance(context: Context) : RoomDB{
            if(instance==null){
                instance = Room.databaseBuilder(context.applicationContext, RoomDB::class.java, DB_NAME)
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