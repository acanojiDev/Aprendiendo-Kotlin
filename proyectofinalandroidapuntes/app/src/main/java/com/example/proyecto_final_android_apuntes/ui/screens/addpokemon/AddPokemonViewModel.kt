package com.example.proyecto_final_android_apuntes.ui.screens.addpokemon

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
class AddPokemonViewModel @Inject constructor(
    private val pokemonRepository: IPokemonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<AddPokemonUiState>(AddPokemonUiState.Idle)
    val uiState: StateFlow<AddPokemonUiState> = _uiState.asStateFlow()
    private val _name = MutableStateFlow("")
    private val _type = MutableStateFlow("")
    private val _level = MutableStateFlow(1)
    private val _hp = MutableStateFlow(1)

    val name: StateFlow<String> = _name.asStateFlow()
    val type: StateFlow<String> = _type.asStateFlow()
    val level: StateFlow<Int> = _level.asStateFlow()
    val hp: StateFlow<Int> = _hp.asStateFlow()

    fun setName(newName: String){
        if(newName.isNotEmpty())
            _name.value = newName
    }
    fun setType(newType: String){
        if(newType.isNotEmpty())
            _type.value = newType
    }
    fun setLevel(newLevel: Int){
        _level.value = newLevel
    }
    fun setHp(newHp: Int){
        _hp.value = newHp
    }

    fun addPokemon(){
        _uiState.value = AddPokemonUiState.Loading

        viewModelScope.launch{
            try {
                val nextId = (pokemonRepository.getAllPokemon().maxByOrNull { it.id }?.id ?: 0) + 1 //Encuentro el id mas alto
                val newPokemon = Pokemon(
                    id=nextId,
                    name=_name.value,
                    type=_type.value,
                    level=_level.value,
                    hp=_hp.value
                )
                pokemonRepository.addPokemon(newPokemon)
                _uiState.value = AddPokemonUiState.Success
                clearForm()
            }catch (e: Exception){
                _uiState.value = AddPokemonUiState.Error("Error: ${e.message}")
            }
        }
    }
    // Función para limpiar
    private fun clearForm(){
        _name.value = ""
        _type.value = ""
        _level.value = 1
        _hp.value = 1
        _uiState.value = AddPokemonUiState.Idle
    }
}
sealed class AddPokemonUiState {
    data object Idle : AddPokemonUiState() //<-Formulario vacio
    data object Loading : AddPokemonUiState()
    data object Success : AddPokemonUiState()
    data class Error(val message: String) : AddPokemonUiState()
}