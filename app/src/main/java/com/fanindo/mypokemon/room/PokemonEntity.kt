package com.fanindo.mypokemon.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "poke")
data class PokemonEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "nickName")
    var nickName: String? = null ,

    @ColumnInfo(name = "photo")
    var photo: String? = null ,

    @ColumnInfo(name = "moves")
    var moves: String? = null,

    @ColumnInfo(name = "types")
    var types: String? = null,


    ) : Parcelable
