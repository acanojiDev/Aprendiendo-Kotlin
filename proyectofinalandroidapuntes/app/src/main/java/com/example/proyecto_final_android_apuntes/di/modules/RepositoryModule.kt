package com.example.proyecto_final_android_apuntes.di

import com.example.proyecto_final_android_apuntes.data.repository.IPokemonRepository
import com.example.proyecto_final_android_apuntes.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Module: Le dice a Hilt que esta es una clase módulo
 * que proporciona dependencias
 */
@Module
/**
 * @InstallIn(SingletonComponent::class): Indica que este módulo
 * estará disponible durante TODA la vida de la aplicación
 * (Solo hay una instancia)
 */
@InstallIn(SingletonComponent::class)
/**
 * abstract class: Debe ser abstracta cuando usas @Binds
 */
abstract class RepositoryModule {

    /**
     * @Binds: Vincula una interfaz a su implementación
     *
     * Significa: "Cuando alguien pida IPokemonRepository,
     * dame una instancia de PokemonRepository"
     */
    @Binds
    /**
     * @Singleton: Solo hay una instancia en toda la app
     * Todas las clases comparten la MISMA instancia
     */
    @Singleton
    /**
     * Función abstracta que recibe la implementación (PokemonRepository)
     * y retorna la interfaz (IPokemonRepository)
     */
    abstract fun bindPokemonRepository(
        impl: PokemonRepository
    ): IPokemonRepository
}