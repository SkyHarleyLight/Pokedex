package com.example.pokedex.data

import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import io.reactivex.Single

class MockPokemonRepository: PokemonRepository {
    private val items = mutableListOf<PokemonEntity>(
        PokemonEntity("1", "bulbasaur", 69, 7, generateUrlFromId(1)),
        PokemonEntity("2", "ivysaur", 130, 10, generateUrlFromId(2)),
        PokemonEntity("3", "venusaur", 100, 20, generateUrlFromId(3)),
        PokemonEntity("4", "charmander", 40, 14, generateUrlFromId(4)),
        PokemonEntity("5", "charmeleon", 35, 15, generateUrlFromId(5))
    )

    override fun getPokemonList(): Single<List<PokemonEntity>> =
            Single.just(items)

    override fun getPokemonById(id: String): Single<PokemonEntity> {
        val pokemon = items.find { it.id == id }

        return if(pokemon != null){
            Single.just(pokemon)
        } else {
            Single.error(Throwable("Not found"))
        }
    }

    private fun generateUrlFromId(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}