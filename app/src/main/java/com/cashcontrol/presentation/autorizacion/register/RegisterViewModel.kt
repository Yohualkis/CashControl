package com.cashcontrol.presentation.autorizacion.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import com.cashcontrol.data.repositories.AutorizacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val autorizacionRepository: AutorizacionRepository,
) : ViewModel() {

    companion object {
        private const val REQUIRED_FIELD = "Este campo es obligatorio *"
    }

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NombreChange -> onNombreChange(event.nombre)
            is RegisterEvent.EmailChange -> onEmailChange(event.email)
            is RegisterEvent.PasswordChange -> onPasswordChange(event.password)
            is RegisterEvent.ConfirmPasswordChange -> onConfirmPasswordChange(event.password)

            RegisterEvent.Register -> onRegister()
            RegisterEvent.LimpiarClaveYErrorContrasena -> onLimpiarClaveYErrorContrasena()
            RegisterEvent.LimpiarErrores -> onLimpiarErrores()
        }
    }

    private fun onRegister() {
        viewModelScope.launch {
            if (errorRegister())
                return@launch

            autorizacionRepository.register(
                usuarioRequestDto = UsuarioRequestDto(
                    usuarioId = 0,
                    nombre = _uiState.value.nombre,
                    contrasena = _uiState.value.password,
                    email = _uiState.value.email,
                    fotoPath = null,
                )
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorGeneral = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        onLimpiarErrores()
                        _uiState.update {
                            autorizacionRepository.login(it.email!!, it.password!!)
                            it.copy(
                                isLoading = false,
                                usuario = null,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onLimpiarErrores() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorNombre = "",
                    errorEmail = "",
                    errorPassword = "",
                    errorConfirmPassword = "",
                    errorGeneral = "",
                )
            }
        }
    }

    private fun errorRegister(): Boolean {
        var hasErrors = false

        if (_uiState.value.nombre.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorNombre = REQUIRED_FIELD)
            }
            hasErrors = true
        } else if (_uiState.value.nombre!!.length < 3) {
            _uiState.update {
                it.copy(errorNombre = "Mínimo 3 caracteres *")
            }
            hasErrors = true
        }

        if (_uiState.value.email.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorEmail = REQUIRED_FIELD)
            }
            hasErrors = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email!!).matches()) {
            _uiState.update {
                it.copy(errorEmail = "Ingrese un email válido *")
            }
            hasErrors = true
        }

        if (_uiState.value.password.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorPassword = REQUIRED_FIELD)
            }
            hasErrors = true
        } else if (_uiState.value.password!!.length < 8) {
            _uiState.update {
                it.copy(errorPassword = "Mínimo 8 caracteres *")
            }
            hasErrors = true
        }

        if (_uiState.value.confirmPassword.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorConfirmPassword = REQUIRED_FIELD)
            }
            hasErrors = true
        } else if (_uiState.value.password != _uiState.value.confirmPassword) {
            _uiState.update {
                it.copy(
                    errorConfirmPassword = "Las contraseñas no coinciden *"
                )
            }
            hasErrors = true
        }

        return hasErrors
    }

    private fun onLimpiarClaveYErrorContrasena() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    confirmPassword = "",
                    errorConfirmPassword = "",
                )
            }
        }
    }

    private fun onConfirmPasswordChange(pass: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    confirmPassword = pass,
                    errorConfirmPassword = ""
                )
            }
        }
    }

    private fun onPasswordChange(pass: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    password = pass,
                    errorPassword = ""
                )
            }
        }
    }

    private fun onEmailChange(email: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    email = email,
                    errorEmail = ""
                )
            }
        }
    }

    private fun onNombreChange(nombre: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    nombre = nombre,
                    errorNombre = ""
                )
            }
        }
    }
}