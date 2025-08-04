package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.TransaccionEntity

@Dao
sealed interface TransaccionDao {
    @Upsert
    suspend fun save(transaccion: TransaccionEntity)

    @Upsert
    suspend fun saveLista(listaTransacciones: List<TransaccionEntity>)

    @Query("""
        select *
        from Transacciones
        where transaccionId = :id
        limit 1
    """)
    suspend fun find(id: Long?): TransaccionEntity?

    @Delete
    suspend fun delete(transaccion: TransaccionEntity)

    @Query("""
        select *
        from Transacciones t
        inner join Categorias c on t.categoriaId = c.categoriaId
        where t.usuarioId = :usuarioId
        and c.usuarioId = :usuarioId
        and c.tipo = :tipoCategoria
        """)
    suspend fun getTransaccionesPorUsuarioYCategoria(
        usuarioId: Long,
        tipoCategoria: String
    ): List<TransaccionEntity>
}