package com.cashcontrol.presentation.categoria

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Segment
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Segment
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cashcontrol.R
import com.cashcontrol.presentation.composables.CashControlAppBar
import com.cashcontrol.presentation.composables.MensajeDeErrorGenerico
import com.cashcontrol.presentation.composables.TextfieldGenerico
import okhttp3.internal.notify

@Composable
fun CategoriaScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    categoriaId: Long?,
    goBack: () -> Unit,
) {
    LaunchedEffect(categoriaId) {
        categoriaId?.let {
            viewModel.selectedCategoria(categoriaId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CategoriaFormulario(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaFormulario(
    uiState: CategoriaUiState,
    onEvent: (CategoriaEvent) -> Unit,
    goBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceDim,
        topBar = {
            CashControlAppBar(
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                onActionPressed = goBack
            )
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusManager.clearFocus() }
        ) {
            var tipoSeleccionado by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            val tiposCategoria = listOf("GASTOS", "INGRESOS")

            // CAMPO DESCRIPCION
            TextfieldGenerico(
                value = uiState.descripcion ?: "",
                onValueChange = {
                    onEvent(CategoriaEvent.DescripcionChange(it))
                },
                labelResource = R.string.campo_descripcion,
                icono = Icons.AutoMirrored.Filled.Article,
                errorMessagePass = uiState.errorDescripcion
            )
            MensajeDeErrorGenerico(uiState.errorDescripcion)

            // CAMPO TIPO DE CATEGORIA
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = if (uiState.tipoCategoria.isNullOrBlank()) "" else uiState.tipoCategoria,
                    onValueChange = {
                        onEvent(CategoriaEvent.TipoCategoriaChange(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Segment,
                            contentDescription = ""
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(
                        text = "Tipo de categoría",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.outline
                    ) },
                    trailingIcon = {
                        TrailingIcon(expanded = expanded)
                    },
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
                    isError = !uiState.errorTipoCategoria.isNullOrBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tiposCategoria.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                onEvent(CategoriaEvent.TipoCategoriaChange(tipo))
                                expanded = false
                            }
                        )
                    }
                }
            }
            MensajeDeErrorGenerico(uiState.errorTipoCategoria)
            Spacer(modifier = Modifier.height(8.dp))

            // BTN GUARDAR
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onEvent(CategoriaEvent.Save)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(R.string.btn_guardar),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.btn_guardar),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            MensajeDeErrorGenerico(uiState.errorGeneral)
        }
    }
}
@Preview
@Composable
fun PreviewCategoriaFormulario() {
    CategoriaFormulario(
        uiState = CategoriaUiState(),
        onEvent = {},
        goBack = {}
    )
}