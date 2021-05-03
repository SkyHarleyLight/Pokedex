package com.example.pokedex.data

import com.example.pokedex.data.network.PokemonApiService
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import io.reactivex.Observable
import io.reactivex.Single

class NetworkPokemonRepository(
    val api: PokemonApiService
) : PokemonRepository {
    override fun getPokemonList(): Single<List<PokemonEntity>> {
        return api.fetchPokemonList()
            .flatMap { pokemonList ->
                Observable.fromIterable(pokemonList.results)
                    .flatMapSingle {
                        getPokemonById(it.name)
                    }.toList()
            }
    }

    override fun getPokemonById(id: String): Single<PokemonEntity> {
        return api.fetchPokemonInfo(id)
            .map { serverPokemon ->
                val abilities = serverPokemon.abilities.map { it.ability.name }
                PokemonEntity(
                    id = serverPokemon.id,
                    name = serverPokemon.name,
                    weight = serverPokemon.weight,
                    height = serverPokemon.height,
                    abilities = abilities,
                    imageUrl = generateUrlFromId(serverPokemon.id)
                )
            }
    }

    private fun generateUrlFromId(id: String): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}