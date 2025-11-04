package com.example.proyecto_final_android_apuntes.data.repository

import com.example.proyecto_final_android_apuntes.data.model.Pokemon

interface IPokemonRepository{
    fun getAllPokemon(): List<Pokemon>
    fun getPokemonById(id: Int): Pokemon?
    fun addPokemon(pokemon: Pokemon)
    fun deletePokemon(id: Int)
    fun updatePokemon(pokemon: Pokemon)
    fun getPokemonsbyType(type: String): List<Pokemon>
    fun getPokemonsByHighLevel(level: Int): List<Pokemon>
}