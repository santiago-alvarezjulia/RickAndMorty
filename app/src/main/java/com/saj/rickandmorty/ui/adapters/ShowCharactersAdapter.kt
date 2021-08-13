package com.saj.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saj.rickandmorty.R
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.ui.imagemanager.ImageLoader
import javax.inject.Inject

class ShowCharactersAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ShowCharactersAdapter.ViewHolder>() {

    private val showCharacters = mutableListOf<ShowCharacter>()

    fun setData(newShowCharacters: List<ShowCharacter>) {
        val diffCallback = ShowCharacterDiffCallback(showCharacters, newShowCharacters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        showCharacters.clear()
        showCharacters.addAll(newShowCharacters)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = showCharacters.size

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
        holder.nameTv.text = showCharacter.name
        imageLoader.loadImage(holder.itemView.context, holder.avatarIv, showCharacter.imageUrl)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarIv: ImageView = itemView.findViewById(R.id.character_avatar)
        val nameTv: TextView = itemView.findViewById(R.id.character_name)
    }
}