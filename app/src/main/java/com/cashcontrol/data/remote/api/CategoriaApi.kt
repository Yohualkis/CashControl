package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.CategoriaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {
    // OJO: Como los metodos devuelven un "Response<T>" recordar
    //      que se debe validar manualmente si la respuesta fue
    //      exitosa (response.isSuccessful) o si hubo un error.

    @GET("api/categorias/{usuarioId}")
    suspend fun getCategorias(@Path("usuarioId") usuarioId: Long): Response<List<CategoriaDto>>

    @GET("api/categorias/{usuarioId}/{categoriaId}")
    suspend fun getCategoriaById(@Path("usuarioId") usuarioId: Long, @Path("categoriaId") categoriaId: Long): CategoriaDto

    @POST("api/categorias/{usuarioId}")
    suspend fun postCategoria(@Path("usuarioId") usuarioId: Long, @Body categoria: CategoriaDto): Resource<CategoriaDto>

    @PUT("api/categorias/{usuarioId}/{categoriaId}")
    suspend fun putCategoria(@Path("usuarioId") usuarioId: Long, @Path("categoriaId") categoriaId: Long, @Body categoria: CategoriaDto): Resource<CategoriaDto>

    @DELETE("api/categorias/{usuarioId}/{categoriaId}")
    suspend fun deleteCategoria(@Path("usuarioId") usuarioId: Long, @Path("categoriaId") categoriaId: Long)
}