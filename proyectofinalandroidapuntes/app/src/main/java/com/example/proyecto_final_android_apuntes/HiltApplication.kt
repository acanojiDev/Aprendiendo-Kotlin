package com.example.proyecto_final_android_apuntes

import android.app.Application
import com.example.proyecto_final_android_apuntes.data.repository.IPokemonRepository
import com.example.proyecto_final_android_apuntes.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para inyecciones del repository
 */

@HiltAndroidApp
class HiltApplication : Application()