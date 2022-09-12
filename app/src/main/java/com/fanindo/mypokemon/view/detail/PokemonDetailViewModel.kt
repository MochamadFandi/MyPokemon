package com.fanindo.mypokemon.view.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.fanindo.mypokemon.room.PokeRepository
import com.fanindo.mypokemon.room.PokemonEntity

class PokemonDetailViewModel(application: Application): ViewModel() {
    private val repo: PokeRepository = PokeRepository(application)

    fun insert(poke: PokemonEntity){
        repo.insert(poke)
    }

    fun delete(poke: PokemonEntity) {
        repo.delete(poke)
    }

}