package com.example.pokedex.presentation.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import java.lang.IllegalStateException

private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class MainAdapter(
    private val onItemClicked: (id: String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

    fun setPokemonList(pokemons: List<DisplayableItem>){
        items.clear()
        items.addAll(pokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.pokemon_item, parent, false)
                PokemonViewHolder(view, onItemClicked)
            }
            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemToShow = items[position]

        when(itemToShow){
            is PokemonItem -> {
                (holder as PokemonViewHolder).bind(itemToShow)
            }
            is HeaderItem -> {
                (holder as HeaderViewHolder).bind(itemToShow)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is PokemonItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    class PokemonViewHolder(view: View, val onItemClicked: (id: String) -> Unit): RecyclerView.ViewHolder(view){
        private val textView = itemView.findViewById<TextView>(R.id.nameTv)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)

        fun bind(item: PokemonItem){
            textView.text = item.name

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imagePreview)

            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val headerText = itemView.findViewById<TextView>(R.id.headerText)

        fun bind(item: HeaderItem){
            headerText.text = item.text
        }
    }
}