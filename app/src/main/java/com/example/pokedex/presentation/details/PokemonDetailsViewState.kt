package com.example.pokedex.presentation.details

sealed class PokemonDetailsViewState {
    object Loading: PokemonDetailsViewState()

    data class ErrorState(val errorMessage: String) : PokemonDetailsViewState()

    data class ContentState(
        val name: String,
        val weight: Int,
        val height: Int,
        val imageUrl: String,
        val abilities: List<String>
    ): PokemonDetailsViewState()
}