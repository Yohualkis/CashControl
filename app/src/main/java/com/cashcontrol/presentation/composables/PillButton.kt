package com.cashcontrol.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PillButton(
    texto: String,
    estadoInicialSeleccion: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (estadoInicialSeleccion) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            backgroundColor = if (estadoInicialSeleccion) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(texto)
    }
}

@Composable
@Preview
fun PreviewPillButton(){
    PillButton(
        texto = "Ejemplo",
        onClick = {}
    )
}