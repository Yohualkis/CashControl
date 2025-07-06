package com.cashcontrol.data.remote

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.dto.CategoriaDto
import com.cashcontrol.data.remote.dto.LoginRequestDto
import com.cashcontrol.data.remote.dto.UsuarioDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaApi,
    private val autorizacionApi: AutorizacionApi
) {
    // CATEGORIAS
    suspend fun getCategorias(usuarioId: Long) = categoriaApi.getCategorias(usuarioId)

    suspend fun getCategoriaById(usuarioId: Long, categoriaId: Long) =
        categoriaApi.getCategoriaById(usuarioId, categoriaId)

    suspend fun postCategoria(usuarioId: Long, categoria: CategoriaDto) =
        categoriaApi.postCategoria(usuarioId, categoria)

    suspend fun deleteCategoria(usuarioId: Long, categoriaId: Long) =
        categoriaApi.deleteCategoria(usuarioId, categoriaId)

    suspend fun putCategoria(usuarioId: Long, categoriaId: Long, categoria: CategoriaDto) =
        categoriaApi.putCategoria(usuarioId, categoriaId, categoria)

    // AUTORIZACION
    suspend fun login(loginRequest: LoginRequestDto) = autorizacionApi.login(loginRequest)
    suspend fun register(usuarioDto: UsuarioDto) = autorizacionApi.register(usuarioDto)
}