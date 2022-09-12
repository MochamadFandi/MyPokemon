package com.fanindo.mypokemon.view.pokemon

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fanindo.mypokemon.R
import com.fanindo.mypokemon.databinding.ActivityMainBinding
import com.fanindo.mypokemon.model.Pokemon
import com.fanindo.mypokemon.room.PokemonEntity
import com.fanindo.mypokemon.view.ViewModelFactory
import com.fanindo.mypokemon.view.detail.PokemonDetailActivity
import com.fanindo.mypokemon.view.detail.PokemonDetailActivity.Companion.EXTRA_DATA
import com.fanindo.mypokemon.view.favorite.MyPokeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]
        mainViewModel.getPokemon()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listPoke.observe(this) { poke ->
            setRecyclerView(poke)
        }
    }

    private fun setRecyclerView(poke: ArrayList<Pokemon>) {
        val adapter = PokemonAdapter()
        adapter.onItemClick = { selectedPokemon ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, setData(selectedPokemon))
            startActivity(intent)
        }
        adapter.setData(poke)
        binding.rvPoke.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvPoke.layoutManager = GridLayoutManager(this, 4)
        } else {
            binding.rvPoke.layoutManager = GridLayoutManager(this, 2)
        }

    }

    private fun setData(poke: Pokemon): PokemonEntity {
        val arrayType = arrayListOf<String>()
        val arrayMove = arrayListOf<String>()
        poke.types?.forEach {
            it.type?.name?.let { it1 -> arrayType.add(it1) }
        }
        poke.moves?.forEach {
            it.move?.name?.let { it1 -> arrayMove.add(it1) }
        }

        return PokemonEntity(
            name = poke.name,
            photo = poke.picture,
            types = arrayType.toString(),
            moves = arrayMove.toString()
        )
    }

    private fun setupAction() {

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, MyPokeActivity::class.java))
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.double_back), Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}