package com.example.githubuserapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ItemListUserBinding

class UserAdapter(private val listUser: List<ItemsItem>) : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    class MyViewHolder(val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemsItem){
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.ivProfil)
            binding.tvNama.text = items.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: ItemsItem)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }
}