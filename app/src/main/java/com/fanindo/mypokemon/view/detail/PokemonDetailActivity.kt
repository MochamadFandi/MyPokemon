package com.fanindo.mypokemon.view.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fanindo.mypokemon.R
import com.fanindo.mypokemon.databinding.ActivityPokemonDetailBinding
import com.fanindo.mypokemon.room.PokemonEntity
import com.fanindo.mypokemon.view.ViewModelFactory
import com.fanindo.mypokemon.view.favorite.MyPokeActivity
import com.fanindo.mypokemon.view.pokemon.MainActivity
import kotlin.random.Random

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    private lateinit var entity: PokemonEntity
    private var stateSave: Boolean = false

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAV = "extra_fav"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.btnCatch.setOnClickListener {
            val random = Random.nextInt(0, 100)
            if (random <= 50) {
                Toast.makeText(this, getString(R.string.succes_catch), Toast.LENGTH_SHORT).show()
                showDialogInsert()
            } else {
                Toast.makeText(this, getString(R.string.fled), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRelease.setOnClickListener {
            showDialogDelete()
        }

        binding.ivBack.setOnClickListener {
            if (stateSave) {
                startActivity(Intent(this, MyPokeActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()

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

        val fav = intent.getParcelableExtra<PokemonEntity>(EXTRA_FAV)
        val data = intent.getParcelableExtra<PokemonEntity>(EXTRA_DATA)

        if (fav != null) {
            binding.btnCatch.visibility = View.GONE
            binding.btnRelease.visibility = View.VISIBLE
            binding.tvNickname.visibility = View.VISIBLE
            binding.tvNickname.visibility = View.VISIBLE
            binding.tvNickText.visibility = View.VISIBLE
            binding.tvNickname.text = fav.nickName
            stateSave = true

            setData(fav)
        } else if (data != null) {
            binding.btnCatch.visibility = View.VISIBLE
            binding.btnRelease.visibility = View.GONE
            binding.tvNickname.visibility = View.GONE
            binding.tvNickname.visibility = View.GONE
            binding.tvNickText.visibility = View.GONE
            stateSave = false
            setData(data)
        }
    }

    private fun setData(data: PokemonEntity) {
        binding.tvName.text = data.name
        binding.tvType.text = data.types
        binding.tvMove.text = data.moves
        Glide.with(this).load(data.photo).into(binding.ivPoke)
        entity = data
    }

    private fun setupViewModel() {
        pokemonDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[PokemonDetailViewModel::class.java]
    }

    private fun showDialogInsert() {
        val input = EditText(this)
        input.hint = getString(R.string.insert_nick)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.paddingStart
        val alert = AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.succes_catch))
            setView(input)
            setCancelable(false)
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                val save = PokemonEntity(
                    name = entity.name,
                    photo = entity.photo,
                    types = entity.types,
                    moves = entity.moves,
                    nickName = input.text.toString()
                )
                pokemonDetailViewModel.insert(save)
                startActivity(Intent(this@PokemonDetailActivity, MyPokeActivity::class.java))
                Toast.makeText(
                    this@PokemonDetailActivity,
                    getString(R.string.save_pokemon),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        }
        val dialog = alert.create()
        dialog.show()
    }

    private fun showDialogDelete() {
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(getString(R.string.release))
            setMessage(getString(R.string.delete_poke))
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                pokemonDetailViewModel.delete(entity)
                Toast.makeText(
                    this@PokemonDetailActivity,
                    getString(R.string.transfer_success),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
