package com.example.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.MockPokemonRepository
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository

class MainViewModel: ViewModel() {
    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListLiveData = MutableLiveData<List<PokemonEntity>>()
    fun getPokemonList(): LiveData<List<PokemonEntity>> = _pokemonListLiveData

    fun loadData(){
        val pokemons = repository.getPokemonList()
        _pokemonListLiveData.value = pokemons
    }
}