package com.example.navgraph_aprendiendo.ui.navigation

sealed class Destinations(val route:String){
    data object Home:Destinations(route="home")
    data object Detail: Destinations(route ="detail/{id}"){
        fun createRoute(id: String)="detail/$id"
    }
    data object Settings: Destinations(route="settings")
}

//Centralizamos todas las rutas