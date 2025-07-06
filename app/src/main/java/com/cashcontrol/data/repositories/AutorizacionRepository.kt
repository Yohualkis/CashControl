package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.remote.RemoteDataSource
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.LoginRequestDto
import com.cashcontrol.data.remote.dto.UsuarioDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AutorizacionRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: UsuarioRepository
) {
    fun login(email: String, password: String): Flow<Resource<UsuarioEntity>> {
        return flow {
            emit(Resource.Loading())
            try {
                val reponse = remote.login(LoginRequestDto(email, password))
                val dtoToEntity = reponse.usuario.toEntity()
                local.save(dtoToEntity)
                emit(Resource.Success(dtoToEntity))
            } catch (e: HttpException) {
                emit(Resource.Error("Email o contraseña incorrectos"))
            } catch (e: Exception) {
                emit(Resource.Error("Error desconocido: " + e.message))
            }
        }
    }

    fun register(usuarioDto: UsuarioDto): Flow<Resource<UsuarioEntity>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = remote.register(usuarioDto)
                val dtoToEntity = response.toEntity()
                emit(Resource.Success(dtoToEntity))
            } catch (e: HttpException) {
                emit(Resource.Error("Error de conexión: " + e.message))
            } catch (e: Exception) {
                emit(Resource.Error("Error desconocido: " + e.message))
            }
        }
    }
}