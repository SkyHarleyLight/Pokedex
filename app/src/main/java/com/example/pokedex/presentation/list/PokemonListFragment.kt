package com.example.pokedex.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonListFragmentBinding
import com.example.pokedex.presentation.list.adapter.DisplayableItem
import com.example.pokedex.presentation.list.adapter.MainAdapter

class PokemonListFragment : Fragment() {
    private val pokemonListViewModel: PokemonListViewModel = PokemonListViewModel()
    private var adapter: MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: PokemonListFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.pokemon_list_fragment,
            container,
            false
        )

        adapter = MainAdapter(
            onItemClicked = { id ->
                val action = PokemonListFragmentDirections.actionPokemonListDestinationToPokemonDetailsDestination(id)
                findNavController().navigate(action)
            }
        )

        binding.pokemonListViewModel = pokemonListViewModel
        binding.pokemonListRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        loadData(binding)

        return binding.root
    }

    private fun loadData(binding: PokemonListFragmentBinding){
        pokemonListViewModel.loadData()

        pokemonListViewModel.viewState().observe(viewLifecycleOwner) { viewState ->
            when(viewState){
                is PokemonListViewState.LoadingState -> {
                    binding.progressBarPokemonList.isVisible = true
                    binding.pokemonListRecyclerView.isVisible = false
                    binding.errorMessageTextPokemonList.isVisible = false
                }
                is PokemonListViewState.ContentState -> {
                    binding.progressBarPokemonList.isVisible = false
                    binding.pokemonListRecyclerView.isVisible = true
                    binding.errorMessageTextPokemonList.isVisible = false

                    showData(viewState.items)
                }
                is PokemonListViewState.ErrorState -> {
                    binding.errorMessageTextPokemonList.isVisible = true
                }
            }
        }
    }

    private fun showData(items: List<DisplayableItem>){
        adapter?.setPokemonList(items)
    }
}