package com.example.proyecto_final_android_apuntes.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyecto_final_android_apuntes.ui.screens.addpokemon.AddPokemonScreen
import com.example.proyecto_final_android_apuntes.ui.screens.addpokemon.AddPokemonViewModel
import com.example.proyecto_final_android_apuntes.ui.screens.pokemondetail.PokemonDetailScreen
import com.example.proyecto_final_android_apuntes.ui.screens.pokemondetail.PokemonDetailViewModel
import com.example.proyecto_final_android_apuntes.ui.screens.pokemonlist.PokemonListScreen
import com.example.proyecto_final_android_apuntes.ui.screens.pokemonlist.PokemonListViewModel

/** Extensoon para agregar todas las rutas al NavGraph
 * Centraliza la definicion de pantallas ya rgumentos
 */
fun NavGraphBuilder.appNavGraph(navController: NavController) {

    /**
     * Pantalla de Lista de Pokémon
     */
    composable(route = Destinations.PokemonList.route) {
        val viewModel: PokemonListViewModel = hiltViewModel()

        PokemonListScreen(
            viewModel = viewModel,
            onNavigateToDetail = { id ->
                navController.navigate(Destinations.PokemonDetail.createRoute(id))
            },
            onNavigateToAdd = {
                navController.navigate(Destinations.AddPokemon.route)
            }
        )
    }
    //Pantalla Detalle Pokemon
    composable(
        route= Destinations.PokemonDetail.route,
        arguments = listOf(
            navArgument("id"){
                type= NavType.IntType
            }
        )
    ){
        val viewModel: PokemonDetailViewModel = hiltViewModel()
        PokemonDetailScreen(
            viewModel = viewModel,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }

    //Pantalla Create

    composable(
        route= Destinations.AddPokemon.route
    ){
        val viewModel: AddPokemonViewModel = hiltViewModel()
        AddPokemonScreen(
            viewModel = viewModel,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}