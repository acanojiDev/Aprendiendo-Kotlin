package com.example.navgraph_aprendiendo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Bienvenido a Home")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {onNavigateToDetail("123")}
        ){
            Text("Ir a Detalle")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavigateToSettings
        ){
            Text("Ir a Configuración")
        }
    }
}