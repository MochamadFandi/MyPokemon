package com.fanindo.mypokemon.room

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PokeRepository(application: Application) {
    private val pokemonDao: PokemonDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PokeDatabase.getInstance(application)
        pokemonDao = db.pokeDao()
    }

    fun getFavPoke(): LiveData<List<PokemonEntity>> = pokemonDao.getAllPoke()

    fun insert(poke: PokemonEntity) {
        executorService.execute { pokemonDao.insertPoke(poke) }
    }

    fun delete(poke: PokemonEntity) {
        executorService.execute { pokemonDao.deletePoke(poke) }
    }



}