package com.example.pokedex.presentation

import com.example.pokedex.presentation.adapter.PokemonItem

sealed class MainViewState {
    object LoadingState : MainViewState()
    data class ErrorState(val errorMessage: String) : MainViewState()
    data class ContentState(val items: List<PokemonItem>) : MainViewState()
}