package com.saj.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saj.rickandmorty.R
import com.saj.rickandmorty.databinding.ShowCharacterItemBinding
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.ui.imagemanager.ImageLoader

class ShowCharactersAdapter(private val imageLoader: ImageLoader,
                            private val onClick: (ShowCharacter) -> Unit)
    : RecyclerView.Adapter<ShowCharactersAdapter.ViewHolder>() {

    private val showCharacters = mutableListOf<ShowCharacter>()

    fun setData(newShowCharacters: List<ShowCharacter>) {
        val diffCallback = ShowCharacterDiffCallback(showCharacters, newShowCharacters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        showCharacters.clear()
        showCharacters.addAll(newShowCharacters)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = showCharacters.size

    override fun getItemId(position: Int): Long {
        return showCharacters[position].id.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowCharactersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_character_item, parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowCharactersAdapter.ViewHolder, position: Int) {
        val showCharacter = showCharacters[position]
        holder.binding.characterName.text = showCharacter.name
        imageLoader.loadImage(holder.itemView.context, holder.binding.characterAvatar,
            showCharacter.imageUrl)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ShowCharacterItemBinding.bind(itemView)
        init {
            itemView.setOnClickListener {
                onClick(showCharacters[adapterPosition])
            }
        }
    }
}