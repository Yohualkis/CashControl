package com.cashcontrol.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cashcontrol.R


@Composable
fun CashControlAppBar(
    goBack: () -> Unit ,
    onActionClick: () -> Unit = {},
    icono: ImageVector,
    titulo: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { goBack() }
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(
                onClick = { onActionClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.cambiar_foto),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun PreviewCashControlAppBar() {
    MaterialTheme {
        CashControlAppBar(
            icono = Icons.Default.Menu,
            goBack = { },
            onActionClick = {},
            titulo = "Cash Control",
        )
    }
}
