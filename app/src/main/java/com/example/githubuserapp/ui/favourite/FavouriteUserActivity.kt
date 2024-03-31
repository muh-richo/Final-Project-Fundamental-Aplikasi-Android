package com.example.githubuserapp.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityFavouriteUserBinding
import com.example.githubuserapp.ui.viewmodelfactory.FavouriteViewModelFactory

class FavouriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteUserBinding
    private lateinit var viewModel: FavouriteUserViewModel
    private lateinit var adapter: FavouriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = FavouriteViewModelFactory.getInstance(this@FavouriteUserActivity.application)
        viewModel = ViewModelProvider(this@FavouriteUserActivity, factory).get(FavouriteUserViewModel::class.java)

        adapter = FavouriteUserAdapter()

        binding.rvFavouriteUser.layoutManager = LinearLayoutManager(this@FavouriteUserActivity)
        binding.rvFavouriteUser.setHasFixedSize(false)
        binding.rvFavouriteUser.adapter = this.adapter

        viewModel.getAllFavUser().observe(this) { favUser ->
            if (favUser != null) {
                adapter.setListFavUser(favUser)
            }
        }
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }
}