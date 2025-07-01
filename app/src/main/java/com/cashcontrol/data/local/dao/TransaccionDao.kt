package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.TransaccionEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface TransaccionDao {
    @Upsert
    suspend fun save(transaccion: TransaccionEntity)

    @Query("""
        select *
        from Transacciones
        where transaccionId = :id
        limit 1
    """)
    suspend fun find(id: Long?): TransaccionEntity?

    @Delete
    suspend fun delete(transaccion: TransaccionEntity)

    @Query("select * from Transacciones")
    fun getAll(): Flow<List<TransaccionEntity?>>
}