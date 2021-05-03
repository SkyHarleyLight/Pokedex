package com.example.pokedex.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonDetailsFragmentBinding

class PokemonDetailsFragment : Fragment(){
    private val pokemonDetailsViewModel: PokemonDetailsViewModel = PokemonDetailsViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: PokemonDetailsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.pokemon_details_fragment,
            container,
            false
        )

        binding.pokemonDetailsViewModel = pokemonDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val id = PokemonDetailsFragmentArgs.fromBundle(requireArguments()).pokemonId
        loadData(id, binding)

        return binding.root
    }

    private fun loadData(
        id: String,
        binding: PokemonDetailsFragmentBinding
    ){
        pokemonDetailsViewModel.loadPokemonById(id)

        pokemonDetailsViewModel.viewState().observe(viewLifecycleOwner) { viewState ->
            when(viewState){
                PokemonDetailsViewState.Loading -> {
                    binding.progressBarPokemonDetails.isVisible = true
                    binding.contentGroupPokemonDetails.isVisible = false
                    binding.errorMessageTextPokemonDetails.isVisible = false
                }
                is PokemonDetailsViewState.ContentState -> {
                    binding.progressBarPokemonDetails.isVisible = false
                    binding.contentGroupPokemonDetails.isVisible = true
                    binding.errorMessageTextPokemonDetails.isVisible = false

                    showData(binding, viewState)
                }
                is PokemonDetailsViewState.ErrorState -> {
                    binding.progressBarPokemonDetails.isVisible = false
                    binding.contentGroupPokemonDetails.isVisible = false
                    binding.errorMessageTextPokemonDetails.isVisible = true
                }
            }
        }
    }

    private fun showData(
        binding: PokemonDetailsFragmentBinding,
        pokemonDetails: PokemonDetailsViewState.ContentState
    ){
        binding.namePokemonDetails.text = pokemonDetails.name
        binding.weightPokemonDetails.text = pokemonDetails.weight.toString()
        binding.heightPokemonDetails.text = pokemonDetails.height.toString()
        binding.abilitiesPokemonDetails.text = pokemonDetails.abilities.joinToString(", ")
        Glide.with(binding.imagePokemonDetails.context)
            .load(pokemonDetails.imageUrl)
            .into(binding.imagePokemonDetails)
    }
}