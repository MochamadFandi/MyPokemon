package com.fanindo.mypokemon.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokeDatabase? = null

        fun getInstance(context: Context): PokeDatabase {
            if (INSTANCE == null) {
                synchronized(PokeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PokeDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as PokeDatabase
        }
    }
}