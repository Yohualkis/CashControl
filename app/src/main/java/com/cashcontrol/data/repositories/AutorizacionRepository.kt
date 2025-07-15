package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.remote.RemoteDataSource
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.AutorizacionResponseDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AutorizacionRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: UsuarioRepository,
) {
    fun login(email: String, password: String): Flow<Resource<AutorizacionResponseDto>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = remote.login(AutorizacionRequestDto(email, password))
                val usuarioLocal = response.usuario.toEntity()
                local.saveUser(usuarioLocal)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error("Email o contraseña incorrectos"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }
        }
    }

    suspend fun logout() = local.deleteUser()
    suspend fun getUser() = local.getUser()

    fun register(usuarioRequestDto: UsuarioRequestDto): Flow<Resource<UsuarioEntity>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = remote.register(usuarioRequestDto)
                val dtoToEntity = response.toEntity()
                emit(Resource.Success(dtoToEntity))
            } catch (e: HttpException) {
                emit(Resource.Error("Este email ya está ocupado"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }
        }
    }
}