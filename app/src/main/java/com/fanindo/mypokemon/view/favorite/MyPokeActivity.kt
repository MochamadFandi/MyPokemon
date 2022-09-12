package com.fanindo.mypokemon.view.favorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fanindo.mypokemon.databinding.ActivityFavoriteBinding
import com.fanindo.mypokemon.room.PokemonEntity
import com.fanindo.mypokemon.view.ViewModelFactory
import com.fanindo.mypokemon.view.detail.PokemonDetailActivity
import com.fanindo.mypokemon.view.detail.PokemonDetailActivity.Companion.EXTRA_FAV
import com.fanindo.mypokemon.view.pokemon.MainActivity

class MyPokeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var myPokeViewModel: MyPokeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.ivWild.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun setupViewModel() {
        myPokeViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MyPokeViewModel::class.java]

        myPokeViewModel.getPokeFav().observe(this) { list ->
            if (list != null) {
                setRecyclerView(list)
            }
        }

    }

    private fun setRecyclerView(list: List<PokemonEntity>) {
        val adapter = MyPokeAdapter()
        adapter.onItemClick = { selectedPokemon ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra(EXTRA_FAV, selectedPokemon)
            startActivity(intent)
        }
        adapter.setFav(list)
        binding.rvFav.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFav.layoutManager = GridLayoutManager(this, 4)
        } else {
            binding.rvFav.layoutManager = GridLayoutManager(this, 2)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}