package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface CategoriaDao {
    @Upsert
    suspend fun save(categoria: CategoriaEntity)

    @Query(
        """
            select *
            from Categorias
            where categoriaId = :id
            limit 1
        """
    )
    suspend fun find(id: Int?): CategoriaEntity?

    @Delete
    suspend fun delete(categoria: CategoriaEntity)

    @Query("select * from Categorias")
    fun getAll(): Flow<List<CategoriaEntity>>
}