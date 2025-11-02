package com.example.navgraph_aprendiendo.ui.screens

// ui/screens/DetailScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    id:String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text(text ="Detalles del Item: $id")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateBack
        ){
            Text("Volver Atrás")
        }
    }
}