// ui/screens/pokemonlist/PokemonListScreen.kt
package com.example.proyecto_final_android_apuntes.ui.screens.pokemonlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_final_android_apuntes.data.model.Pokemon

/**
 * Pantalla de Lista de Pokémon
 *
 * @param viewModel: Recibe el ViewModel para acceder al estado
 * @param onNavigateToDetail: Callback para navegar al detalle
 * @param onNavigateToAdd: Callback para navegar a agregar pokémon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Convertimos los StateFlow a State para que Compose pueda reaccionar
    val uiState = viewModel.uiState.collectAsState()
    val pokemonList = viewModel.pokemonList.collectAsState()
    val selectedType = viewModel.selectedType.collectAsState()
    val selectedLevel = viewModel.selectedLevel.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokémon") },
                actions = {
                    // Botón para agregar pokémon
                    IconButton(onClick = onNavigateToAdd) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Mostramos diferentes UI según el estado
            when (val state = uiState.value) {
                // Mientras carga
                is PokemonListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Cuando cargó exitosamente
                is PokemonListUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Filtros por tipo
                        FilterChips(
                            selectedType = selectedType.value,
                            onFilterClick = { type ->
                                if (selectedType.value == type) {
                                    viewModel.clearFilter()
                                } else {
                                    viewModel.filterByType(type)
                                }
                            }
                        )
                        FilterLevel(
                            selected = selectedLevel.value,
                            onFilterClick = { level ->
                                if(selectedLevel.value == level){
                                    viewModel.clearFilter()
                                }else{
                                    viewModel.filterByLevel(level)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Lista de pokémon
                        if (pokemonList.value.isEmpty()) {
                            Text(
                                text = "No hay Pokémon",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(pokemonList.value) { pokemon ->
                                    PokemonListItem(
                                        pokemon = pokemon,
                                        onItemClick = { onNavigateToDetail(pokemon.id) },
                                        onDeleteClick = { viewModel.deletePokemon(pokemon.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                // Cuando ocurre un error
                is PokemonListUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message)
                        Button(onClick = { viewModel.loadPokemon() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterLevel(selected: Int?, onFilterClick: (Int) -> Unit) {
    val levels = listOf(0, 25, 50, 99)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        levels.forEach { level ->
            FilterChip(
                selected = selected == level,
                onClick = { onFilterClick(level) },
                label = { Text("Lvl $level") }
            )
        }
    }
}



/**
 * Composable para los chips de filtro
 */
@Composable
private fun FilterChips(
    selectedType: String?,
    onFilterClick: (String) -> Unit
) {
    val types = listOf("Electric", "Fire", "Water")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        types.forEach { type ->
            FilterChip(
                selected = selectedType == type,
                onClick = { onFilterClick(type) },
                label = { Text(type) }
            )
        }
    }
}



/**
 * Composable para cada item de la lista
 */
@Composable
private fun PokemonListItem(
    pokemon: Pokemon,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = onItemClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${pokemon.type} | Lvl ${pokemon.level}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("❤️ ${pokemon.hp}")
                IconButton(onClick = onDeleteClick) {
                    Text("🗑️")
                }
            }
        }
    }
}