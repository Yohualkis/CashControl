package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.AutorizacionResponseDto
import com.cashcontrol.data.remote.dto.LoginRequestDto
import com.cashcontrol.data.remote.dto.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizacionApi {
    @POST("api/autorizacion/login")
    suspend fun login(@Body request: LoginRequestDto): AutorizacionResponseDto

    @POST("api/autorizacion/register")
    suspend fun register(@Body usuarioDto: UsuarioDto): UsuarioDto
}