package com.cashcontrol.di

import androidx.room.TypeConverter
import java.util.Date

class DateAdapter {
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millis: Long?): Date? = millis?.let { Date(it) }
}