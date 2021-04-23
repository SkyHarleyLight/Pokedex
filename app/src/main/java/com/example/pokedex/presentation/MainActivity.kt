package com.example.pokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.presentation.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel = MainViewModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)

        viewModel.getPokemonList().observe(this, Observer { pokemonList ->
            adapter.setPokemonList(pokemonList)
        })

        viewModel.loadData()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}