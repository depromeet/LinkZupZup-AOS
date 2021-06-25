package com.depromeet.linkzupzup.architecture.domainLayer.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import java.util.*

@Entity(
    tableName = "PersonalLink",
    primaryKeys = ["link_id"],
    indices = [Index(value = ["link_id", "link_url", "link_completed"], unique = true)])
class PersonalLinkEntity (

    @ColumnInfo(name="link_id")
    var id: Int = 0,

    @ColumnInfo(name="link_url")
    var url: String = "",

    @ColumnInfo(name="link_completed")
    var completed: Boolean = false,

    @ColumnInfo(name="link_alarm_enable")
    var alarmEnable: Boolean = false,

    @ColumnInfo(name="link_alarm_dt")
    var alarmDt: Date? = null,

    @ColumnInfo(name="link_created_dt")
    var createdDt: Date = Date())


