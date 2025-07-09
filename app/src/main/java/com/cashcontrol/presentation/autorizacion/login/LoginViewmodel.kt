package com.cashcontrol.presentation.autorizacion.login

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashcontrol.data.SessionManager
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.repositories.AutorizacionRepository
import com.cashcontrol.session.Sesion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val autorizacionRepository: AutorizacionRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onCheckSesionActual()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ContrasenaChange -> onContrasenaChange(event.contrasena)
            is LoginEvent.EmailChange -> onEmailChange(event.email)

            LoginEvent.LimpiarErrores -> limpiarErrores()
            LoginEvent.LimpiarClaveYErrorContrasena -> limpiarCampoYErrorContrasena()
            LoginEvent.Login -> onLogin()
            LoginEvent.CheckSesionActual -> onCheckSesionActual()
            LoginEvent.Logout -> onLogout()
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            autorizacionRepository.logout()
            _uiState.update {
                it.copy(
                    isLoggedIn = false,
                    usuario = null,
                    email = "",
                    contrasena = ""
                )
            }
        }
    }

    private fun onCheckSesionActual() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = autorizacionRepository.getUser()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = user != null,
                    usuario = user
                )
            }
        }
    }

    private fun onLogin() {
        viewModelScope.launch {
            if (errorLogin())
                return@launch

            autorizacionRepository.login(
                email = _uiState.value.email?.lowercase() ?: "",
                password = _uiState.value.contrasena ?: ""
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        limpiarErrores()
                        Sesion.token = result.data?.token
                        sessionManager.saveToken(Sesion.token)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                usuario = result.data?.usuario,
                                isLoggedIn = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun errorLogin(): Boolean {
        var errorEncontrado = false
        if (_uiState.value.email.isNullOrBlank()) {
            _uiState.update {
                it.copy(emailErrorMessage = "Este campo es obligatorio *")
            }
            errorEncontrado = true
        } else if (!EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) {
            _uiState.update {
                it.copy(emailErrorMessage = "Ingrese un email válido *")
            }
            errorEncontrado = true
        }
        if (_uiState.value.contrasena.isNullOrBlank()) {
            _uiState.update {
                it.copy(contrasenaErrorMessage = "Este campo es obligatorio *")
            }
            errorEncontrado = true
        }
        return errorEncontrado
    }
    private fun onContrasenaChange(contrasena: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    contrasena = contrasena,
                    contrasenaErrorMessage = ""
                )
            }
        }
    }

    private fun onEmailChange(email: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    email = email,
                    emailErrorMessage = "",
                )
            }
        }
    }

    private fun limpiarCampoYErrorContrasena() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    contrasenaErrorMessage = "",
                    contrasena = ""
                )
            }
        }
    }

    private fun limpiarErrores(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    emailErrorMessage = "",
                    contrasenaErrorMessage = "",
                    errorGeneral = "",
                )
            }
        }
    }
}