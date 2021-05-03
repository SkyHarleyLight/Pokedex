package com.example.pokedex.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.createPokedexApiService
import com.example.pokedex.di.Injector
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.presentation.list.adapter.toItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PokemonListViewModel : ViewModel() {
    private val repository = Injector.providePokemonRepository()
    private var disposable: Disposable? = null

    private val _viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = _viewStateLiveData

    fun loadData() {
        _viewStateLiveData.value = PokemonListViewState.LoadingState

        disposable = repository.getPokemonList()
            .map { items -> items.map { it.toItem() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _viewStateLiveData.value = PokemonListViewState.ContentState(it)
            }, {
                _viewStateLiveData.value = PokemonListViewState.ErrorState("Error Message")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}