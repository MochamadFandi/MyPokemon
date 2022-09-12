package com.fanindo.mypokemon.view.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fanindo.mypokemon.R
import com.fanindo.mypokemon.databinding.ItemPokemonBinding
import com.fanindo.mypokemon.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var listPokemon = ArrayList<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null

    fun setData(newList: List<Pokemon>?) {
        if (newList == null) return
        listPokemon.clear()
        listPokemon.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = listPokemon[position]
        holder.bind(pokemon)

    }

    override fun getItemCount() = listPokemon.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(pokemon: Pokemon) {
            with(binding) {
                tvPoke.text = pokemon.name
                Glide.with(itemView.context).load(pokemon.picture).into(ivPoke)
            }
        }


        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listPokemon[adapterPosition])
            }
        }


    }
}