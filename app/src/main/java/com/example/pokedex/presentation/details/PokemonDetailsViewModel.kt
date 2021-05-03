package com.example.pokedex.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.createPokedexApiService
import com.example.pokedex.domain.PokemonRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PokemonDetailsViewModel : ViewModel() {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private var disposable: Disposable? = null

    private val _viewStateLiveData = MutableLiveData<PokemonDetailsViewState>()
    fun viewState(): LiveData<PokemonDetailsViewState> = _viewStateLiveData

    fun loadPokemonById(id: String) {
        _viewStateLiveData.value = PokemonDetailsViewState.Loading

        disposable = repository.getPokemonById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pokemonEntity ->
                _viewStateLiveData.value = PokemonDetailsViewState.ContentState(
                    name = pokemonEntity.name,
                    weight = pokemonEntity.weight,
                    height = pokemonEntity.height,
                    imageUrl = pokemonEntity.imageUrl,
                    abilities = pokemonEntity.abilities
                )
            }, {
                _viewStateLiveData.value = PokemonDetailsViewState.ErrorState("Failed to load pokemon with id=$id")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}