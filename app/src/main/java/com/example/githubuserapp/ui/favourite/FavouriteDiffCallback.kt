package com.example.githubuserapp.ui.favourite

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.database.FavouriteUser

class FavouriteDiffCallback(private val oldNoteList: List<FavouriteUser>, private val newNoteList: List<FavouriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.login == newNote.login && oldNote.htmlUrl == newNote.htmlUrl
    }
}