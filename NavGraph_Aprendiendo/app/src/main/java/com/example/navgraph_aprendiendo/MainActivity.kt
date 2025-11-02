package com.example.navgraph_aprendiendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.navgraph_aprendiendo.ui.navigation.Destinations
import com.example.navgraph_aprendiendo.ui.navigation.appNavGraph
import com.example.navgraph_aprendiendo.ui.theme.NavGraph_AprendiendoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           NavGraph_AprendiendoTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   //Crear el navController
                   val navController = rememberNavController()

                   //NavHost: contenedor que maneja la navegación
                   NavHost(
                       navController=navController,
                       startDestination = Destinations.Home.route,
                       modifier = Modifier.fillMaxSize()
                   ){
                       appNavGraph(navController)
                   }
               }
           }
        }
    }
}
