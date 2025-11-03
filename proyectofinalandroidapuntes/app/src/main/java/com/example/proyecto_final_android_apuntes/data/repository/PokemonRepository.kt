package com.example.proyecto_final_android_apuntes.data.repository

import com.example.proyecto_final_android_apuntes.data.model.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(): IPokemonRepository{
    private val pokemonList = mutableListOf<Pokemon>(
        Pokemon(1,"Picachu","Electric",25,35),
        Pokemon(2,"Charizard","Fire",36,78),
        Pokemon(3,"Blastoise","Water",36,79),
    )

    //Obtener todos los Pokémon
    override fun getAllPokemon(): List<Pokemon>{
        return pokemonList
    }

    //Obtener pokemon por ID
    override fun getPokemonById(id:Int):Pokemon?{
        return pokemonList.find {it.id == id}
    }

    //Aggregate un nuevo Pokemon
    override fun addPokemon(pokemon: Pokemon){
        pokemonList.add(pokemon)
    }

    //Eliminate un pokemon por ID
    override fun deletePokemon(id:Int){
        pokemonList.removeAll {it.id == id}
    }

    //Actualizar un Pokémon
    override fun updatePokemon(pokemon: Pokemon){
        val index = pokemonList.indexOfFirst { it.id == pokemon.id }
        if(index !=-1){
            pokemonList[index] = pokemon
        }
    }

    //Obtener Pokemon por tipo
    override fun getPokemonsbyType(type:String):List<Pokemon>{
        return pokemonList.filter {it.type == type}
    }
}