package com.example.pokedex.presentation.list

import com.example.pokedex.presentation.list.adapter.PokemonItem

sealed class PokemonListViewState {
    object LoadingState : PokemonListViewState()
    data class ErrorState(val errorMessage: String) : PokemonListViewState()
    data class ContentState(val items: List<PokemonItem>) : PokemonListViewState()
}