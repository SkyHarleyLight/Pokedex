package com.example.pokedex.domain

interface PokemonRepository {
    fun getPokemonList(): List<PokemonEntity>
    fun addNewPokemon(pokemon: PokemonEntity)
}