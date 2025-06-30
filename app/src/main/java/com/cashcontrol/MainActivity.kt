package com.cashcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.navigation.compose.rememberNavController
import com.cashcontrol.presentation.navigation.CashControlNavHost
import com.cashcontrol.ui.theme.CashControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashControlTheme {
                CashControlNavHost(
                    navHostController = rememberNavController()
                )
            }
        }
    }
}