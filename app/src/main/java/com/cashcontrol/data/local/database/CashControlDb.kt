package com.cashcontrol.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cashcontrol.Converters
import com.cashcontrol.data.local.dao.TransaccionDao
import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.data.local.entities.CategoriaEntity

@Database(
    entities = [
        TransaccionEntity::class,
        CategoriaEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CashControlDb: RoomDatabase() {
    abstract fun TransaccionDao(): TransaccionDao
}