package com.cashcontrol.di

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateAdapter {
    private val formatter = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss", Locale.getDefault())

    @ToJson
    fun toJson(date: Date): String {
        return formatter.format(date)
    }

    @FromJson
    fun fromJson(dateString: String): Date {
        return formatter.parse(dateString) ?: Date()
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millis: Long?): Date? = millis?.let { Date(it) }
}