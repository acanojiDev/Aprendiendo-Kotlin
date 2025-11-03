package com.example.proyecto_final_android_apuntes

import com.example.proyecto_final_android_apuntes.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para inyecciones del repository
 */

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule{

    /**
     * Binds: vincula la interfaz a su implementación
     */
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        impl: PokemonRepository
    ): IPokemonRepository
}