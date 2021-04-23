package com.example.pokedex.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.domain.PokemonEntity

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val items: MutableList<PokemonEntity> = emptyList<PokemonEntity>().toMutableList()

    fun setPokemonList(pokemons: List<PokemonEntity>){
        items.clear()
        items.addAll(pokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        val itemToShow = items[position]
        holder.bind(itemToShow)
    }

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val textView = itemView.findViewById<TextView>(R.id.nameTv)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)

        fun bind(item: PokemonEntity){
            textView.text = item.name

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imagePreview)

        }
    }
}