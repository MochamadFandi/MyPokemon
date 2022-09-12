package com.fanindo.mypokemon.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fanindo.mypokemon.R
import com.fanindo.mypokemon.databinding.ItemPokemonBinding
import com.fanindo.mypokemon.room.PokemonEntity

class MyPokeAdapter : RecyclerView.Adapter<MyPokeAdapter.ViewHolder>() {

    private var listFav = ArrayList<PokemonEntity>()
    var onItemClick: ((PokemonEntity) -> Unit)? = null

    fun setFav(newList: List<PokemonEntity>?) {
        if (newList == null) return
        listFav.clear()
        listFav.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = listFav[position]
        holder.bind(pokemon)
    }

    override fun getItemCount() = listFav.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(pokemon: PokemonEntity) {
            with(binding) {
                tvPoke.text = pokemon.nickName
                Glide.with(itemView.context).load(pokemon.photo).into(ivPoke)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listFav[adapterPosition])
            }
        }
    }
}