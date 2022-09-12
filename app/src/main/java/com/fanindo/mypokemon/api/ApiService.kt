package com.fanindo.mypokemon.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("pokemon")
    fun getPokemon(): Call<ResponsePokemon>

    @GET
    fun getDetailPokemon(@Url url: String): Call<ResponsePokemonDetail>
}