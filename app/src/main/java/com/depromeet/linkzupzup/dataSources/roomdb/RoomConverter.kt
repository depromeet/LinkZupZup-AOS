package com.depromeet.linkzupzup.dataSources.roomdb

import androidx.room.TypeConverter
import java.sql.*

class RoomConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}