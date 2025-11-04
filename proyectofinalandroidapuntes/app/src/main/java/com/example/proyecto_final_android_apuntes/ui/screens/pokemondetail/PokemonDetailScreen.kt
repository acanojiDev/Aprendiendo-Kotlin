// ui/screens/pokemondetail/PokemonDetailScreen.kt
package com.example.proyecto_final_android_apuntes.ui.screens.pokemondetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val pokemon = viewModel.pokemon.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Detalles") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            when (val state = uiState.value) {
                is PokemonDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is PokemonDetailUiState.Success -> {
                    pokemon.value?.let { poke ->
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Avatar
                            Card(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("🐱", style = MaterialTheme.typography.displaySmall)
                                }
                            }

                            // Nombre
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Nombre", style = MaterialTheme.typography.labelSmall)
                                    Text(poke.name, style = MaterialTheme.typography.bodyLarge)
                                }
                            }

                            // Tipo
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Tipo", style = MaterialTheme.typography.labelSmall)
                                    Text(poke.type, style = MaterialTheme.typography.bodyLarge)
                                }
                            }

                            // Level
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Level", style = MaterialTheme.typography.labelSmall)
                                        Text(poke.level.toString(), style = MaterialTheme.typography.bodyLarge)
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(onClick = { if (poke.level > 1) viewModel.updateLevel(poke.level - 1) }, modifier = Modifier.size(40.dp)) {
                                            Text("-")
                                        }
                                        Button(onClick = { viewModel.updateLevel(poke.level + 1) }, modifier = Modifier.size(40.dp)) {
                                            Text("+")
                                        }
                                    }
                                }
                            }

                            // HP
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("HP", style = MaterialTheme.typography.labelSmall)
                                    Text("❤️ ${poke.hp}", style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }
                }

                is PokemonDetailUiState.Error -> {
                    Text(state.message, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}