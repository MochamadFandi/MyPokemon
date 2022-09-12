package com.fanindo.mypokemon.view.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fanindo.mypokemon.room.PokeRepository
import com.fanindo.mypokemon.room.PokemonEntity

class MyPokeViewModel(application: Application): ViewModel() {
    private val repo: PokeRepository = PokeRepository(application)

    fun getPokeFav(): LiveData<List<PokemonEntity>> = repo.getFavPoke()

}