package com.example.pokedex.di

import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.createPokedexApiService
import com.example.pokedex.domain.PokemonRepository

object Injector {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    fun providePokemonRepository(): PokemonRepository = repository
}