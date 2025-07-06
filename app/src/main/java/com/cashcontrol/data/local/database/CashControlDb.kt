package com.cashcontrol.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cashcontrol.data.local.dao.CategoriaDao
import com.cashcontrol.data.local.dao.MetaDao
import com.cashcontrol.data.local.dao.SugerenciaDao
import com.cashcontrol.data.local.dao.TransaccionDao
import com.cashcontrol.data.local.dao.UsuarioDao
import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.data.local.entities.MetaDetalleEntity
import com.cashcontrol.data.local.entities.MetaEntity
import com.cashcontrol.data.local.entities.SugerenciaDetalleEntity
import com.cashcontrol.data.local.entities.SugerenciaEntity
import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.di.DateAdapter

@Database(
    entities = [
        TransaccionEntity::class,
        CategoriaEntity::class,
        UsuarioEntity::class,
        MetaEntity::class,
        MetaDetalleEntity::class,
        SugerenciaEntity::class,
        SugerenciaDetalleEntity::class,
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateAdapter::class)
abstract class CashControlDb: RoomDatabase() {
    abstract fun TransaccionDao(): TransaccionDao
    abstract fun CategoriaDao(): CategoriaDao
    abstract fun MetaDao(): MetaDao
    abstract fun SugerenciaDao(): SugerenciaDao
    abstract fun UsuarioDao(): UsuarioDao
}