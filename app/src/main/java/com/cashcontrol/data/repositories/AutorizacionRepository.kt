package com.cashcontrol.data.repositories

import androidx.datastore.core.IOException
import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.remote.RemoteDataSource
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import com.cashcontrol.data.remote.dto.UsuarioResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AutorizacionRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val userRepo: UsuarioRepository,
) {
    fun login(email: String, password: String): Flow<Resource<UsuarioResponseDto>> {
        return flow {
            emit(Resource.Loading())
            try {
                val usuarioFetched = remote.login(AutorizacionRequestDto(email, password))
                userRepo.saveUser(usuarioFetched.toEntity())
                emit(Resource.Success(usuarioFetched))
            } catch (e: HttpException) {
                emit(Resource.Error("Email o contraseña incorrectos *"))
            } catch (e: IOException) {
                emit(Resource.Error("Error de conexión: ${e.message}"))
            } catch (e: Exception) {
                emit(Resource.Error("Error inesperado: ${e.message}"))
            }
        }
    }

    suspend fun logout() = userRepo.deleteUser()
    suspend fun getUser() = userRepo.getUser()

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