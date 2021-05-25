package com.depromeet.linkzupzup.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
interface RoomDAO<T> {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(data : T)
}
