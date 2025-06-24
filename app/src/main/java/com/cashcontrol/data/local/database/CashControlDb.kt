package com.cashcontrol.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cashcontrol.Converters
import com.cashcontrol.data.local.dao.GastoDao
import com.cashcontrol.data.local.entities.GastoEntity
import com.cashcontrol.data.local.entities.CategoriaEntity

@Database(
    entities = [
        GastoEntity::class,
        CategoriaEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CashControlDb: RoomDatabase() {
    abstract fun GastoDao(): GastoDao
}