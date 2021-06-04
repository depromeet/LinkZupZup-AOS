package com.depromeet.linkzupzup.component

import androidx.room.TypeConverter
import java.util.Date

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