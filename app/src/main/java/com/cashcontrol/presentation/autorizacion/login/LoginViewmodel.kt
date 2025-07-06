package com.cashcontrol.presentation.autorizacion.login

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.repositories.AutorizacionRepository
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {

    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ContrasenaChange -> onContrasenaChange(event.contrasena)
            is LoginEvent.EmailChange -> onEmailChange(event.email)

            LoginEvent.LimpiarErrores -> limpiarErrores()
            LoginEvent.LimpiarClaveYErrorContrasena -> limpiarCampoYErrorContrasena()
            LoginEvent.Login -> onLogin()
        }
    }

    private fun onLogin() {
        viewModelScope.launch {
            if (errorLogin())
                return@launch

            autorizacionRepository.login(
                email = _uiState.value.email ?: "",
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
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = "Entraste!",
                                emailErrorMessage = "",
                                contrasenaErrorMessage = "",
                                usuario = result.data,
                                email = "",
                                contrasena = ""
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