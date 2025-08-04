package com.cashcontrol.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextfieldNumericoGenerico(
    value: String,
    onValueChange: (Double) -> Unit,
    labelResource: Int,
    icono: ImageVector,
    errorMessagePass: String?,
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.toDouble()) },
        label = { Text(stringResource(labelResource)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        leadingIcon = {
            Icon(
                imageVector = icono,
                contentDescription = ""
            )
        },
        isError = !errorMessagePass.isNullOrBlank(),
    )
}