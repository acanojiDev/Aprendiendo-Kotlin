package com.example.proyecto_final_android_apuntes.ui.navigation

sealed class Destinations(val route:String){
    data object PokemonList: Destinations(route="pokemon_list")
    data object PokemonDetail: Destinations(route="pokemon_detail/{id}"){
        fun createRoute(id:Int) = "pokemon_detail/$id"
    }
    data object AddPokemon : Destinations(route="add_pokemon")
}