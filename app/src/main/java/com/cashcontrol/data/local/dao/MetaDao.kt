package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cashcontrol.data.local.entities.MetaConDetalleEntity
import com.cashcontrol.data.local.entities.MetaDetalleEntity
import com.cashcontrol.data.local.entities.MetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface MetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeta(meta: MetaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetalle(detalle: MetaDetalleEntity): Long

    // Insertar varios detalles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetalles(detalles: List<MetaDetalleEntity>)

    @Transaction
    @Query(
        """
        select * 
        from Metas 
        where usuarioId = :usuarioId
        """
    )
    fun getMetasConDetalles(usuarioId: Long?): Flow<List<MetaConDetalleEntity>>

    @Delete
    suspend fun deleteMeta(meta: MetaEntity)

    @Delete
    suspend fun deleteDetalle(detalle: MetaDetalleEntity)

    @Update
    suspend fun updateMeta(meta: MetaEntity)

    @Update
    suspend fun updateDetalle(detalle: MetaDetalleEntity)
}