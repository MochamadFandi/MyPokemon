package com.fanindo.mypokemon.model


import android.os.Parcelable
import com.fanindo.mypokemon.api.MovesItem
import com.fanindo.mypokemon.api.TypesItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val name: String?,
    val picture: String?,
    val types: @RawValue List<TypesItem>?,
    val moves: @RawValue List<MovesItem>?

) : Parcelable
