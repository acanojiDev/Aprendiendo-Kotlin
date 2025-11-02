package com.example.navgraph_aprendiendo.ui.navigation

// ui/navigation/NavGraph.kt
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.navgraph_aprendiendo.ui.screens.DetailScreen
import com.example.navgraph_aprendiendo.ui.screens.HomeScreen

/**
 * Extensión para agregar todas las rutas al NavGraph
 * Centraliza la definición de pantallas y argumentos
 */

fun NavGraphBuilder.appNavGraph(navController: NavController){
    //Pantalla Home
    composable(route=Destinations.Home.route){
        HomeScreen(
            onNavigateToDetail = { id ->
                navController.navigate(Destinations.Detail.createRoute(id))
            },
            onNavigateToSettings = {
                navController.navigate(Destinations.Settings.route)
            }
        )
    }

    //Pantalla Detail con argumento
    composable(
        route = Destinations.Detail.route,
        arguments = listOf(
            navArgument("id"){
                type= NavType.StringType
            }
        )
    ){ backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        DetailScreen(
            id = id,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }

    /*
    composable(route = Destinations.Settings.route) {
        SettingsScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }*/
}

//Extension helper para navegar con tipo seguro
fun NavController.navigateToDetail(id:String){
    navigate(Destinations.Detail.createRoute(id))
}