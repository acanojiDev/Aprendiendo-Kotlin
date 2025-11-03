package com.example.proyecto_final_android_apuntes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.example.proyecto_final_android_apuntes.ui.navigation.Destinations
import com.example.proyecto_final_android_apuntes.ui.navigation.
import com.example.proyecto_final_android_apuntes.ui.theme.ProyectofinalandroidapuntesTheme

/**
 * @AndroidEntryPoint: Hilt inyecta dependencias en esta Activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectofinalandroidapuntesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // rememberNavController(): Crea el controlador de navegación
                    val navController = rememberNavController()

                    /**
                     * NavHost: Contenedor que muestra la pantalla actual
                     *
                     * navController: El controlador que maneja la navegación
                     * startDestination: La pantalla inicial
                     */
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.PokemonList.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // appNavGraph: Agrega todas nuestras rutas
                        appNavGraph(navController)
                    }
                }
            }
        }
    }
}