package com.example.pokedex.data.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

fun createPokedexApiService(): PokemonApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(PokemonApiService::class.java)
}

interface PokemonApiService {
    @GET("pokemon")
    fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Single<PokemonListResponse>

    @GET("pokemon/{name}")
    fun fetchPokemonInfo(@Path("name") name: String): Single<PokemonDetailedResponse>
}

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonPartialResponse>
)

data class PokemonPartialResponse(
    val name: String,
    val url: String
)

data class PokemonDetailedResponse(
    val id: String,
    val name: String,
    val weight: Int,
    val height: Int,
    val abilities: List<PokemonAbilityData>
)

data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
    val isHidden: Boolean,
    val slot: Int
)

data class PokemonAbilityDetailsData(
    val name: String,
    val url: String
)