package com.cashcontrol.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Category
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.Lightbulb
import androidx.compose.material.icons.twotone.Payments
import androidx.compose.material.icons.twotone.Savings
import androidx.compose.material.icons.twotone.SportsScore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cashcontrol.presentation.navigation.Screen

@Composable
fun AppBottomBar(
    pantallaActual: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomNavItems = remember { getBottomNavItems() }

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icono, contentDescription = item.titulo) },
                label = { Text(item.titulo) },
                selected = isSameScreenType(pantallaActual, item.screen),
                onClick = { onNavigate(item.screen) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

// Funcion para comparar pantallas sin modificar la clase original
private fun isSameScreenType(current: Screen, target: Screen): Boolean {
    return when {
        current is Screen.Dashboard && target is Screen.Dashboard -> true
        current is Screen.ListaCategorias && target is Screen.ListaCategorias -> true
        current is Screen.Sugerencias && target is Screen.Sugerencias -> true
        current is Screen.Categoria && target is Screen.ListaCategorias -> true
        else -> false
    }
}

// Clase auxiliar para los items del BottomBar
private data class BottomNavItem(
    val screen: Screen,
    val titulo: String,
    val icono: ImageVector
)

// Lista de items del BottomBar
private fun getBottomNavItems(): List<BottomNavItem> = listOf(
    BottomNavItem(
        screen = Screen.Dashboard,
        titulo = "Dashboard",
        icono = Icons.TwoTone.Dashboard
    ),
    BottomNavItem(
        screen = Screen.ListaTransacciones,
        titulo = "Tras.",
        icono = Icons.TwoTone.Payments
    ),
    BottomNavItem(
        screen = Screen.ListaCategorias,
        titulo = "Categorías",
        icono = Icons.TwoTone.Category
    ),
    BottomNavItem(
        screen = Screen.Sugerencias,
        titulo = "Sugerencias",
        icono = Icons.TwoTone.Lightbulb
    ),
    BottomNavItem(
        screen = Screen.Sugerencias,
        titulo = "Metas",
        icono = Icons.TwoTone.Savings
    )
)

@Preview
@Composable
fun Asdd(){
    AppBottomBar(
        pantallaActual = Screen.Dashboard,
        onNavigate = {},
    )
}