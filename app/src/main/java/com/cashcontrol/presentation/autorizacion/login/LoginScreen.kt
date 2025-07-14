package com.cashcontrol.presentation.autorizacion.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cashcontrol.R
import com.cashcontrol.presentation.composables.MensajeDeErrorGenerico

@Composable
fun LoginScreen(
    viewmodel: LoginViewmodel = hiltViewModel(),
    goToDash: () -> Unit,
    goToRegister: () -> Unit,
){
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.isLoggedIn) {
        if(uiState.isLoggedIn){
            goToDash()
        }
    }

    BackHandler(enabled = uiState.isLoggedIn) {
        goToDash()
    }

    LoginScreenView(
        uiState = uiState,
        onEvent = viewmodel::onEvent,
    )
}

@Composable
fun LoginScreenView(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
) {
    var showPass by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF016FCE)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.cashcontrol_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(120.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.surfaceDim
                )
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(R.string.subtitulo_login),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                OutlinedTextField(
                    value = uiState.email ?: "",
                    onValueChange = { onEvent(LoginEvent.EmailChange(it)) },
                    label = { Text(stringResource(R.string.placeholder_email)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.run {
                        textFieldColors(
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            errorCursorColor = MaterialTheme.colorScheme.error,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorLeadingIconColor = MaterialTheme.colorScheme.error,
                            errorTrailingIconColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Icono de email"
                        )
                    },
                    isError = !uiState.emailErrorMessage.isNullOrBlank(),
                )
                MensajeDeErrorGenerico(uiState.emailErrorMessage)

                // Password
                OutlinedTextField(
                    value = uiState.contrasena ?: "",
                    onValueChange = { onEvent(LoginEvent.ContrasenaChange(it)) },
                    label = { Text(stringResource(R.string.placeholder_contraseña)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.run {
                        textFieldColors(
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            errorCursorColor = MaterialTheme.colorScheme.error,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorLeadingIconColor = MaterialTheme.colorScheme.error,
                            errorTrailingIconColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Icono de clave"
                        )
                    },
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(icon, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = !uiState.contrasenaErrorMessage.isNullOrBlank(),
                )

                MensajeDeErrorGenerico(uiState.contrasenaErrorMessage)

                MensajeDeErrorGenerico(uiState.errorGeneral)

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Continuar
                Button(
                    onClick = {
                        onEvent(LoginEvent.Login)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = MaterialTheme.shapes.small,
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    } else
                        Text(text = stringResource(R.string.btn_continuar))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Separador
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stringResource(R.string.otra_opcion),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Google Sign-In Button
                OutlinedButton(
                    onClick = { /* login con Google */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.inverseSurface,
                        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_logo32), // usa el nombre correcto
                        contentDescription = "Google",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.btn_continuar_google))
                }

            }

            // Footer
            Text(
                text = stringResource(R.string.copyright),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    LoginScreenView(
        uiState = LoginUiState(),
        onEvent = {},
    )
}
