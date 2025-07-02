package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.AutorizacionResponseDto
import com.cashcontrol.data.remote.dto.LoginRequestDto
import com.cashcontrol.data.remote.dto.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizacionApi {
    @POST("api/autorizacion/login")
    suspend fun login(@Body request: LoginRequestDto): Response<AutorizacionResponseDto>

    @POST("api/autorizacion/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<AutorizacionResponseDto>
}