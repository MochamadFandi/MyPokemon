package com.fanindo.mypokemon.view.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanindo.mypokemon.api.ApiConfig
import com.fanindo.mypokemon.api.ResponsePokemon
import com.fanindo.mypokemon.api.ResponsePokemonDetail
import com.fanindo.mypokemon.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private lateinit var list: ArrayList<Pokemon>

    private val _listPoke = MutableLiveData<ArrayList<Pokemon>>()
    val listPoke: LiveData<ArrayList<Pokemon>> = _listPoke

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPokemon() {
        _isLoading.value = true
        val call = ApiConfig.getApiService().getPokemon()
        call.enqueue(object : Callback<ResponsePokemon> {
            override fun onResponse(
                call: Call<ResponsePokemon>,
                response: Response<ResponsePokemon>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.results?.forEach { it.url?.let { it1 -> getDetail(it1) } }
                }
            }

            override fun onFailure(call: Call<ResponsePokemon>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    private fun getDetail(url: String) {
        list = ArrayList()
        _isLoading.value = true
        val call = ApiConfig.getApiService().getDetailPokemon(url)
        call.enqueue(object : Callback<ResponsePokemonDetail> {
            override fun onResponse(
                call: Call<ResponsePokemonDetail>,
                response: Response<ResponsePokemonDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val pokemon = Pokemon(
                        name = response.body()?.name,
                        picture = response.body()?.sprites?.frontDefault,
                        types = response.body()?.types,
                        moves = response.body()?.moves
                    )
                    list.add(pokemon)
                    _listPoke.value = list
                }
            }

            override fun onFailure(call: Call<ResponsePokemonDetail>, t: Throwable) {
                _isLoading.value = false
            }
        })

    }

}