package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.CategoriaEntity

@Dao
sealed interface CategoriaDao {
    @Upsert
    suspend fun save(categoria: CategoriaEntity)

    @Upsert
    suspend fun saveLista(categorias: List<CategoriaEntity>)

    @Query(
        """
            select *
            from Categorias
            where categoriaId = :id
            limit 1
        """
    )
    suspend fun find(id: Long?): CategoriaEntity?

    @Delete
    suspend fun delete(categoria: CategoriaEntity)

    @Query("select * from Categorias")
    suspend fun getAll(): List<CategoriaEntity>

    @Query("""
        select * 
        from Categorias
        where tipo = :tipo and usuarioId = :usuarioId
    """)
    suspend fun getByTipoAndUsuarioId(tipo: String, usuarioId: Long?): List<CategoriaEntity>
}