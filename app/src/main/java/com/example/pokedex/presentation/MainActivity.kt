package com.example.pokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.presentation.adapter.DisplayableItem
import com.example.pokedex.presentation.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel = MainViewModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        viewModel.viewState().observe(this) { state ->
            when(state){
                is MainViewState.LoadingState -> showProgress()
                is MainViewState.ContentState -> showData(state.items)
                is MainViewState.ErrorState -> showError(state.errorMessage)
            }
        }

        viewModel.loadData()
    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showProgress(){
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
    }

    private fun showData(items: List<DisplayableItem>){
        adapter.setPokemonList(items)
    }

    private fun showError(errorMessage: String){

    }
}