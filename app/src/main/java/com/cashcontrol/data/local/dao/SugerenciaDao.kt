package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.SugerenciaConDetalleYCategoriaEntity
import com.cashcontrol.data.local.entities.SugerenciaDetalleEntity
import com.cashcontrol.data.local.entities.SugerenciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface SugerenciaDao {
    @Upsert
    suspend fun saveSugerencia(sugerencia: SugerenciaEntity)

    @Upsert
    suspend fun saveDetalle(detalle: SugerenciaDetalleEntity)

    @Query("""
        select *
        from Sugerencias
        where usuarioId = :usuarioId
    """)
    fun getSugerenciaConDetallesYCategoria(usuarioId: Int?): Flow<List<SugerenciaConDetalleYCategoriaEntity>>

}