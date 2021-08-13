package com.saj.rickandmorty.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.saj.rickandmorty.models.ShowCharacter

class ShowCharacterDiffCallback(private val oldList: List<ShowCharacter>,
                                private val newList: List<ShowCharacter>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].isTheSame(oldList[oldItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].isContentTheSame(oldList[oldItemPosition])
    }
}