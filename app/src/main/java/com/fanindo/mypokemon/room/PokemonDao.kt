package com.fanindo.mypokemon.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {

    @Query("SELECT * FROM poke")
    fun getAllPoke(): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoke(poke: PokemonEntity)

    @Delete
    fun deletePoke(poke: PokemonEntity)


}