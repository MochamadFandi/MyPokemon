package com.fanindo.mypokemon.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fanindo.mypokemon.view.detail.PokemonDetailViewModel
import com.fanindo.mypokemon.view.favorite.MyPokeViewModel
import com.fanindo.mypokemon.view.pokemon.MainViewModel

class ViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> {
                PokemonDetailViewModel(application) as T
            }
            modelClass.isAssignableFrom(MyPokeViewModel::class.java) -> {
                MyPokeViewModel(application) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}