package com.example.proyecto_final_android_apuntes.ui.screens.addpokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPokemonScreen(
    viewModel: AddPokemonViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
){
    val name = viewModel.name.collectAsState()
    val type = viewModel.type.collectAsState()
    val level = viewModel.level.collectAsState()
    val hp = viewModel.hp.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Agregar Pokémon") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Mensaje de error
            if (uiState.value is AddPokemonUiState.Error) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = (uiState.value as AddPokemonUiState.Error).message,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Mensaje de éxito
            if (uiState.value is AddPokemonUiState.Success) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✅ Pokémon agregado",
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onNavigateBack) {
                            Text("Volver a la lista")
                        }
                    }
                }
            }

            // Formulario
            if (uiState.value !is AddPokemonUiState.Success) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { viewModel.setName(it) },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


                OutlinedTextField(
                    value = type.value,
                    onValueChange = { viewModel.setType(it) },
                    label = { Text("Tipo (Electric, Fire, Water...)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Level
                OutlinedTextField(
                    value = level.value.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { num -> viewModel.setLevel(num) }
                    },
                    label = { Text("Level") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                // HP
                OutlinedTextField(
                    value = hp.value.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { num -> viewModel.setHp(num) }
                    },
                    label = { Text("HP") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón agregar
                Button(
                    onClick = { viewModel.addPokemon() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.value !is AddPokemonUiState.Loading
                ) {
                    if (uiState.value is AddPokemonUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Agregar Pokémon")
                    }
                }
            }
        }
    }
}