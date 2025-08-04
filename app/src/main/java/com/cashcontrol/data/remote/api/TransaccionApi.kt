package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.TransaccionRequestDto
import com.cashcontrol.data.remote.dto.TransaccionResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransaccionApi {
    @GET("api/transacciones/{usuarioId}/{tipoCategoria}")
    suspend fun getTransaccionesPorUsuarioIdYTipoCategoria(
        @Path("usuarioId") usuarioId: Long,
        @Path("tipoCategoria") tipoCategoria: String,
    ): List<TransaccionResponseDto>

    @POST("api/transacciones")
    suspend fun postTransaccion(@Body request: TransaccionRequestDto): TransaccionResponseDto

    @DELETE("api/transacciones/{transaccionId}")
    suspend fun deleteTransaccion(@Path("transaccionId") transaccionId: Long): Boolean

    @PUT("api/transacciones")
    suspend fun putTransaccion(@Body request: TransaccionRequestDto): TransaccionResponseDto
}