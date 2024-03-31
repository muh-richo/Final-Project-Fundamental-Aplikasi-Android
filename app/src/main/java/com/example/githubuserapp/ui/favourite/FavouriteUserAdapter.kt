package com.example.githubuserapp.ui.favourite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.database.FavouriteUser
import com.example.githubuserapp.databinding.ItemListUserBinding
import com.example.githubuserapp.ui.detailuser.DetailUserActivity

class FavouriteUserAdapter: RecyclerView.Adapter<FavouriteUserAdapter.ViewHolder>() {
    private val listUserFav = ArrayList<FavouriteUser>()

    inner class ViewHolder(val binding: ItemListUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FavouriteUser){
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.ivProfil)
            binding.tvNama.text = items.login

            binding.listUser.setOnClickListener {
                val intent = Intent(it.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, items.login)
                it.context.startActivity(intent)
            }
        }
    }

    fun setListFavUser(listUserFav: List<FavouriteUser>) {
        val diffCallback = FavouriteDiffCallback(this.listUserFav, listUserFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUserFav.clear()
        this.listUserFav.addAll(listUserFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserFav[position])
    }

    override fun getItemCount(): Int = listUserFav.size
}