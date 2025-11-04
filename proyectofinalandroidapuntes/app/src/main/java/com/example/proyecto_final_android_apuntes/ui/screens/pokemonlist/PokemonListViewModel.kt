package com.example.proyecto_final_android_apuntes.ui.screens.pokemonlist


import androidx.lifecycle.ViewModel
import com.example.proyecto_final_android_apuntes.data.model.Pokemon
import com.example.proyecto_final_android_apuntes.data.repository.IPokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel para la lista de Pokémon
 *
 * @HiltViewModel: Le dice a Hilt que inyecte las dependencias automaticamente
 * @Inject constructor: Recibe IPokemonRepository por inyección
 */
@HiltViewModel
class  PokemonListViewModel @Inject constructor(
    private val pokemonRepository: IPokemonRepository
): ViewModel(){
    // Estado privado - solo el ViewModel puede modificarlo
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    // Estado público - las vistas lo pueden leer pero no modificar
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    //Lista de pokemon privada
    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    //Lista pública para que la UI la lea
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList.asStateFlow()

    //Filtro Seleccionado
    private val _selectedType = MutableStateFlow<String?>(null)
    val selectedType: StateFlow<String?> = _selectedType.asStateFlow()

    //Filtro nivel seleccionado
    private val _selectedLevel = MutableStateFlow<Int?>(null)
    val selectedLevel: StateFlow<Int?> = _selectedLevel.asStateFlow()

    init {
        loadPokemon()
    }

    //cargo los pokeeeeeeeemons
    fun loadPokemon(){
        _uiState.value = PokemonListUiState.Loading
        try{
            //Llamo al repo para obtener los pokemon
            val pokemons = pokemonRepository.getAllPokemon()
            _pokemonList.value = pokemons
            _uiState.value = PokemonListUiState.Success
        }catch (e: Exception){
            _uiState.value = PokemonListUiState.Error("Error: ${e.message}")
        }
    }

    //Filtro por tipo
    fun filterByType(type:String){
        _selectedType.value = type
        val filtered = pokemonRepository.getPokemonsbyType(type)
        _pokemonList.value = filtered
    }

    //Filtrar por nivel
    fun filterByLevel(level: Int){
        _selectedLevel.value = level
        val filtered = pokemonRepository.getPokemonsByHighLevel(level)
        _pokemonList.value = filtered
    }

    //Limpio el filtro
    fun clearFilter(){
        _selectedType.value = null
        loadPokemon()
    }

    fun deletePokemon(type: Int){

    }
}

/**
 * Estados posibles de la pantalla
 * Usamos sealed class para que Kotlin obligue a manejar todos los casos
 */
sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data object Success : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}