package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.remote.dto.CategoriaResponseDto
import com.cashcontrol.data.remote.dto.FiltroCategoriaRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {
    @GET("api/categorias")
    suspend fun getCategorias(): List<CategoriaResponseDto>

    @GET("api/categorias/{categoriaId}")
    suspend fun getCategoriaById(@Path("categoriaId") categoriaId: Long): CategoriaResponseDto

    @POST("api/categorias/tipo-y-usuario")
    suspend fun getCategoriaPorUsuarioYTipo(@Body request: FiltroCategoriaRequestDto): List<CategoriaResponseDto>

    @POST("api/categorias")
    suspend fun postCategoria(@Body categoriaRequest: CategoriaRequestDto): CategoriaResponseDto

    @PUT("api/categorias")
    suspend fun putCategoria(@Body request: CategoriaRequestDto): CategoriaResponseDto

    @DELETE("api/categorias/{categoriaId}")
    suspend fun deleteCategoria(@Path("categoriaId") categoriaId: Long?): Boolean
}