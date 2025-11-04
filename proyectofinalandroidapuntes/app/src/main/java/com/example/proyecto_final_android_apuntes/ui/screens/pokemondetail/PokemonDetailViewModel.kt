package com.example.proyecto_final_android_apuntes.ui.screens.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_android_apuntes.data.model.Pokemon
import com.example.proyecto_final_android_apuntes.data.repository.IPokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, //para obtener el ID de la navegación
    private val pokemonsRepository: IPokemonRepository
): ViewModel(){
    //Obtengo el ID de los argumentos de la navegación
    private val pokemonId: Int = checkNotNull(savedStateHandle["id"]).toString().toInt()
    //Estado privado solo el ViewModel puede modificarlo
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    //Estado publico lo pueden leer pero no modificar
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    //Pokemon privado
    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    //Pokemon público para que la UI lo lea
    val pokemon: StateFlow<Pokemon?> = _pokemon.asStateFlow()

    init{
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            try {
                val pokemon = pokemonsRepository.getPokemonById(pokemonId)
                if (pokemon != null) {
                    _pokemon.value = pokemon
                    _uiState.value = PokemonDetailUiState.Success
                } else {
                    _uiState.value = PokemonDetailUiState.Error("Pokemon no encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = PokemonDetailUiState.Error("Error: ${e.message}")
            }
        }
    }

    /**
     * Actualizar el nivel del Pokémon
     */
    fun updateLevel(newLevel: Int) {
        _pokemon.value?.let { currentPokemon ->
            val updated = currentPokemon.copy(level = newLevel)
            viewModelScope.launch {
                pokemonsRepository.updatePokemon(updated)
                _pokemon.value = updated
            }
        }
    }
}


/**
 * Estados posibles de la pantalla
 * Usamos sealed class para que Kotlin obligue a manejar todos los casos
 */

sealed class PokemonDetailUiState{
    data object Loading : PokemonDetailUiState()
    data object Success : PokemonDetailUiState()
    data class Error(val message:String) : PokemonDetailUiState()
}